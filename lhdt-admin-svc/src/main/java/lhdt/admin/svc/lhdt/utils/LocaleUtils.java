package lhdt.admin.svc.lhdt.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import lhdt.admin.svc.lhdt.domain.Key;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocaleUtils {

    public static Locale getUserLocale(HttpServletRequest request) {
        String lang = (String)request.getSession().getAttribute(Key.LANG.name());
        log.info("@@@@@@@@@@@ lang = {}", lang);
        if(lang == null || "".equals(lang)) {
            Locale myLocale = request.getLocale();
            lang = myLocale.getLanguage();
        }
        return new Locale(lang);
    }

}
