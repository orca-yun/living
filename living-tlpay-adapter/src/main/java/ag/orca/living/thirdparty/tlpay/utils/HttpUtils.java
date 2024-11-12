package ag.orca.living.thirdparty.tlpay.utils;

import ag.orca.living.common.OrcaAssert;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * <p>
 * Http工具类
 * </p>
 */
@Slf4j
public final class HttpUtils {
    private HttpUtils() {
    }

    @SneakyThrows
    public static <T, E> E postParamSend(String url,
                                         JSONObject params,
                                         Map<String, Object> header,
                                         Class<T> aClass,
                                         BiFunction<String, Class<T>, E> processor) {
        //获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(
                RequestConfig.custom()
                        .setSocketTimeout(3000)
                        .setConnectTimeout(3000)
                        .build()).build();
        //URL
        url = url + disposeGetParams(params);
        // 获取请求对象
        HttpPost httpPost = new HttpPost(url);
        //设置头部
        setHeader(httpPost, header);
        //打印记录日志
        sendLog("POST Param", url, header, params.toJSONString());
        HttpResponse response = httpClient.execute(httpPost);
        //校验返回结果
        checkResponseStatusCode(response);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        //结果转换处理
        return resultResolver(result, aClass, processor);
    }

    /**
     * 处理GET请求的参数 会对参数进行 URLEncoder.encode处理
     *
     * @param params 参数
     * @return String 格式: 有值："?key=xxx&value:12" 没值: ""
     */
    private static String disposeGetParams(JSONObject params) {
        return buildParams(params, true);
    }

    /**
     * 处理GET请求的参数
     *
     * @param params     参数
     * @param urlEncoder 是否对参数进行 URLEncoder.encode处理
     * @return String
     */
    private static String buildParams(JSONObject params, boolean urlEncoder) {
        //设置参数
        StringBuilder paramsStr = new StringBuilder();
        if (Objects.nonNull(params) && !params.isEmpty()) {
            paramsStr.append("?");
            List<String> paramsList = new ArrayList<>();
            params.forEach((k, v) -> {
                String val = urlEncoder ? URLEncoder.encode(v == null ? "" : v.toString(), StandardCharsets.UTF_8) : v.toString();
                paramsList.add(k + "=" + val);
            });
            paramsStr.append(StringUtils.join(paramsList, "&"));
        }
        return paramsStr.toString();
    }

    /**
     * 设置请求头
     *
     * @param HttpRequestBase http
     * @param header          头部JSON
     */
    private static void setHeader(HttpRequestBase requestBase, Map<String, Object> header) {
        if (Objects.nonNull(header) && !header.isEmpty()) {
            header.forEach((k, v) -> requestBase.setHeader(k, v.toString()));
        }
    }

    /**
     * 请求前准备好后的日志输出
     *
     * @param utl    URL
     * @param header 头部信息
     * @param params 参数信息
     * @param <T>    T
     * @param <E>    T/List<T>
     */
    private static <T, E> void sendLog(String method, String utl, Map<String, Object> header, String params) {
        log.info("{} 请求已经准备就绪 \nURL:{} \nHeader:{} \nParam:{}", method, utl, header, params);
    }

    /**
     * 处理请求状态码 判断是否成功
     *
     * @param response response
     */
    private static void checkResponseStatusCode(HttpResponse response) {
        // 获得状态码  200 成功      404 为找到路径         500 后台程序错误  。。。。
        int statusCode = response.getStatusLine().getStatusCode();
        OrcaAssert.match(statusCode != HttpStatus.SC_OK, MessageFormat.format("三方服务错误, {0}", statusCode));
    }

    /**
     * 返回结果解析
     *
     * @param result    返回结果字符串
     * @param aClass    解析Class类型
     * @param processor 解析器方法
     * @param <T>       T
     * @param <E>       T/List<T>
     * @return 返回期望类型的对象或集合
     */
    private static <T, E> E resultResolver(String result, Class<T> aClass, BiFunction<String, Class<T>, E> processor) {
        E apply = processor.apply(result, aClass);
        log.info("\n返回数据:{}\n解析类型:{}\n处理结果:{}", result, aClass, apply.toString());
        return apply;
    }
}
