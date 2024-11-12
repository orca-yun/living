package ag.orca.living.dubbo.filter;

import ag.orca.living.dubbo.annotation.ExcludeDubboLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;

@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class InterfaceLogFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(InterfaceLogFilter.class);
    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\r\n");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        StopWatch sw = new StopWatch();
        Throwable throwable = null;
        Result result = null;
        try {
            sw.start();
            result = invoker.invoke(invocation);
            if (result != null) {
                throwable = result.getException();
            }
        } catch (Exception e) {
            throwable = e;
            throw e;
        } finally {
            sw.stop();
            boolean p = false;
            if (!invoker.getInterface().isAnnotationPresent(ExcludeDubboLog.class)) {
                try {
                    Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    method.setAccessible(true);
                    if (!method.isAnnotationPresent(ExcludeDubboLog.class)) {
                        p = true;
                    }
                } catch (Exception e) {
                    throwable = e;
                    p = true;
                }
            }

            if (p) {
                InterfaceLog face = buildFace(invoker, invocation);
                face.setCostTime(sw.getLastTaskTimeMillis());
                if (result != null) {
                    try {
                        face.setResultValue(OBJECT_MAPPER.writeValueAsString(result.getValue()));
                    } catch (JsonProcessingException e) {
                        face.setResultValue("result value can't to json, " + result);
                    }
                }

                if (throwable != null) {
                    StringBuilder sb = new StringBuilder();
                    Arrays.stream(throwable.getStackTrace())
                            .forEach(ste ->
                                    sb.append(ste.getClassName()).append(".")
                                            .append(ste.getMethodName()).append(":")
                                            .append(ste.getLineNumber())
                                            .append(LINE_SEPARATOR)
                            );
                    face.setStackTraces(sb.toString());
                    face.setThrowableClass(throwable.getClass().getName());
                    face.setExceptionMsg(throwable.getMessage());
                    log.error("dubbo interface log: {}", face);
                } else {
                    log.info("dubbo interface log: {}", face);
                }
            }

        }

        return result;
    }

    private InterfaceLog buildFace(Invoker<?> invoker, Invocation invocation) {
        InterfaceLog face = new InterfaceLog();
        RpcContextAttachment attachment = RpcContext.getServerContext();

        face.setMethodName(invoker.getInterface().getName() + "." + invocation.getMethodName());
        face.setSenderName(attachment.getAttachment("SENDER_APP_NAME"));
        face.setReceiverName(invoker.getUrl().getParameter("application"));
        face.setSrvGroup(invoker.getUrl().getParameter("group"));
        face.setVersion(invoker.getUrl().getParameter("version"));

        face.setSenderHost(attachment.getRemoteHost() + ":" + attachment.getRemotePort());
        face.setReceiverHost(attachment.getLocalHost() + ":" + attachment.getLocalPort());
        Class[] types = invocation.getParameterTypes();
        if (types != null && types.length > 0) {
            face.setParamTypes(
                    Arrays.stream(types).map(Class::getName).toList()
                            .toArray(new String[types.length]));
        }

        face.setParamValues(
                Arrays.stream(invocation.getArguments())
                        .map(arg -> {
                            try {
                                return OBJECT_MAPPER.writeValueAsString(arg);
                            } catch (JsonProcessingException e) {
                                return "paramValue can't to json, " + arg;
                            }
                        }).toList().toArray()
        );
        return face;
    }
}
