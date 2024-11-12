package ag.orca.living.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static ag.orca.living.common.OrcaConst.I18N_BASE_NAME;

public class I18nUtil {
    public static String getMessage(String key) {
        return getMessage(key, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String key, Locale locale) {
        if (StringUtils.isEmpty(key)) {
            return key;
        }
        Locale target = Objects.isNull(locale) ? LocaleContextHolder.getLocale() : locale;
        String language = target.getLanguage();
        if (StringUtils.startsWith(language, Locale.US.getLanguage())) {
            target = Locale.US;
        } else if (StringUtils.startsWith(language, Locale.JAPAN.getLanguage())) {
            target = Locale.JAPAN;
        } else {
            target = Locale.CHINA;
        }

        ResourceBundle bundle = ResourceBundle.getBundle(I18N_BASE_NAME, target);
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        }
        return key;
    }
}
