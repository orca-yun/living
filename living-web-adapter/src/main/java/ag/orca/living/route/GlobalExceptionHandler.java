package ag.orca.living.route;

import ag.orca.living.common.OrcaResult;
import ag.orca.living.errors.ErrorCodes;
import ag.orca.living.errors.OrcaException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.TimeoutException;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {

    private static final String PARAM_ERR_MSG = "输入参数有误";

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public OrcaResult<Void> handleNotSupportException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.info("请求地址'{}',发生NotSupportException.", requestURI);
        return OrcaResult.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OrcaResult<Void> handleNoHandleException(NoHandlerFoundException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.info("请求地址'{}',发生NoHandlerFoundException.", requestURI);
        return OrcaResult.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(value = {
            HttpMessageConversionException.class,
            MethodArgumentNotValidException.class,
            BindException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public OrcaResult<Void> handleException(Exception e, HttpServletRequest request) {
        String message = "";
        if (e instanceof HttpMessageConversionException) {
            Throwable throwable = e.getCause();
            if (Objects.nonNull(throwable) && throwable instanceof InvalidFormatException) {
                message = "输入参数" + ((InvalidFormatException) e.getCause()).getValue().toString() + "有误";
            }
        } else if (e instanceof MethodArgumentNotValidException ex) {
            message = ex.getBindingResult().hasFieldErrors()
                    ? Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()
                    : PARAM_ERR_MSG;
        } else if (e instanceof ConstraintViolationException ex) {
            message = !ObjectUtils.isEmpty(ex.getConstraintViolations())
                    ? ex.getConstraintViolations().stream().limit(1).toList().get(0).getMessage()
                    : PARAM_ERR_MSG;
        } else if (e instanceof MethodArgumentTypeMismatchException ex) {
            message = "输入参数" + ex.getValue() + "有误";
        } else if (e instanceof MissingServletRequestParameterException ex) {
            message = ex.getMessage();
        } else if (e instanceof BindException ex) {
            Optional<String> s = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst();
            message = s.orElseGet(e::getMessage);
        }
        String requestURI = request.getRequestURI();
        log.info("请求地址'{}',发生 Exception. {}", requestURI, message);
        return OrcaResult.fail(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(value = {OrcaException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public OrcaResult<Void> handleOrcaException(OrcaException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.warn("请求地址'{}',发生 OrcaException.{}", requestURI, e.getMessage());
        Throwable ex = rootCause(e);
        return OrcaResult.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }


    @ExceptionHandler(value = {
            RemotingException.class,
            TimeoutException.class
    })
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    public OrcaResult<Void> handleRemotingException(RemotingException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生RemotingException.", requestURI, e);
        Throwable ex = rootCause(e);
        String msg = ex.getMessage();
        return OrcaResult.fail(HttpStatus.REQUEST_TIMEOUT.value(), msg);
    }

    @ExceptionHandler(value = RpcException.class)
    public OrcaResult<Void> handleRpcException(RpcException e, HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生RpcException.", requestURI, e);
        HttpStatus status;
        if (e.isBiz() || e.isValidation() || e.getCode() == ErrorCodes.PARAM_ERR.getCode()) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e.isForbidden() || e.isLimitExceed()) {
            status = HttpStatus.FORBIDDEN;
        } else if (e.isTimeout()) {
            status = HttpStatus.REQUEST_TIMEOUT;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        response.setStatus(status.value());
        Throwable ex = rootCause(e);
        String msg = ex.getMessage();

        return OrcaResult.fail(status.value(), msg);
    }


    @ExceptionHandler(value = RuntimeException.class)
    public OrcaResult<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生RuntimeException.", requestURI, e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Throwable ex = rootCause(e);
        String msg = ex.getMessage();
        response.setStatus(status.value());
        return OrcaResult.fail(status.value(), msg);
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public OrcaResult<Void> handleThrowable(Throwable e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生Throwable.", requestURI, e);
        Throwable ex = rootCause(e);
        String msg = ex.getMessage();
        return OrcaResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static Throwable rootCause(Throwable e) {
        if (Objects.isNull(e)) {
            throw new NullPointerException("NPE ROOT CAUSE");
        }
        Throwable ex = e.getCause();
        if (Objects.nonNull(ex)) {
            return rootCause(ex);
        } else {
            return e;
        }
    }


}
