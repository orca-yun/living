package ag.orca.living.controller;

import ag.orca.living.api.room.LivingRoomShortUrlService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "短链接")
@Controller
@RestController
public class RedirectController {

    @DubboReference
    LivingRoomShortUrlService livingRoomShortUrlService;

    @GetMapping("/{randomStr}")
    public ModelAndView redirectUrl(@PathVariable("randomStr") String randomStr) {
        String url = livingRoomShortUrlService.findRedirectUrl(randomStr);
        RedirectView redirectView = new RedirectView(url, true, true, false);
        // true for exposeModelAttributes, true for exposePathVariables, false for preserveExposedModelAttributes
        return new ModelAndView(redirectView);
    }
}
