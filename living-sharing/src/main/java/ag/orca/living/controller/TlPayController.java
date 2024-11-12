package ag.orca.living.controller;

import ag.orca.living.api.tlpay.TongLianService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

import static ag.orca.living.common.OrcaConst.PAY_FAILED;
import static ag.orca.living.common.OrcaConst.PAY_SUCCESS;

/**
 * <p>
 * 通联支付Controller
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/v3/tl")
public class TlPayController extends AbstractShareController {

    @DubboReference
    private TongLianService tongLianService;


    /**
     * 支付回调
     *
     * @param request  request
     * @param response response
     * @param orderId  订单号
     */
    @SneakyThrows
    @Operation(summary = "通联支付回调")
    @PostMapping("/pay/notify/{orderId}")
    public void payNotify(HttpServletRequest request, HttpServletResponse response, @PathVariable Long orderId) {
        //通知传输的编码为GBK，强制转UTF-8
        String respContent = PAY_SUCCESS;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            TreeMap<String, String> params = getParams(request);
            log.info("通联回调 -> 参数：{}", params);
            tongLianService.payNotify(params, orderId);
        } catch (Throwable e) {
            log.error("通联支付回调处理失败 - {}", orderId, e);
            respContent = PAY_FAILED;
        } finally {
            response.getOutputStream().write(respContent.getBytes());
            response.flushBuffer();
        }
    }

    private TreeMap<String, String> getParams(HttpServletRequest request) {
        TreeMap<String, String> map = new TreeMap<>();
        Map reqMap = request.getParameterMap();
        for (Object key : reqMap.keySet()) {
            String value = ((String[]) reqMap.get(key))[0];
            map.put(key.toString(), value);
        }
        return map;
    }


}
