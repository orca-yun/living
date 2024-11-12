package ag.orca.living.controller;

import ag.orca.living.api.share.ShareUserStatService;
import ag.orca.living.common.OrcaResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v3/stat")
@Tag(name = "用户观看统计[优先 SOCKET IO方式, 二次统计上报]")
public class ShareUserStatController extends AbstractShareController {

    @DubboReference
    ShareUserStatService shareUserStatService;

    @Operation(summary = "上报时间")
    @PostMapping("/report")
    public OrcaResult<Void> report(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        shareUserStatService.reportStat(getAuthInfo(), ua);
        return OrcaResult.success();
    }
}
