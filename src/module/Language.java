package module;

import java.util.Map;
import java.util.TreeMap;

public class Language {

    private static Language language;
    public Map<String, String> mapLang = new TreeMap<>();

    private Language() {

    }

    public static Language getLanguage() {
        if (language == null) {
            language = new Language();
            language.init();
        }
        return language;
    }

    private void init() {
        mapLang.put("af", "AFRIKAANS");
        mapLang.put("sq", "ALBANIAN");
        mapLang.put("ar", "ARABIC");
        mapLang.put("hy", "ARMENIAN");
        mapLang.put("az", "AZERBAIJANI");
        mapLang.put("eu", "BASQUE");
        mapLang.put("be", "BELARUSIAN");
        mapLang.put("bn", "BENGALI");
        mapLang.put("bg", "BULGARIAN");
        mapLang.put("ca", "CATALAN");
        mapLang.put("zh-CN", "CHINESE");
        mapLang.put("hr", "CROATIAN");
        mapLang.put("cs", "CZECH");
        mapLang.put("da", "DANISH");
        mapLang.put("nl", "DUTCH");
        mapLang.put("en", "ENGLISH");
        mapLang.put("et", "ESTONIAN");
        mapLang.put("tl", "FILIPINO");
        mapLang.put("fi", "FINNISH");
        mapLang.put("fr", "FRENCH");
        mapLang.put("gl", "GALICIAN");
        mapLang.put("ka", "GEORGIAN");
        mapLang.put("de", "GERMAN");
        mapLang.put("el", "GREEK");
        mapLang.put("gu", "GUJARATI");
        mapLang.put("ht", "HAITIAN_CREOLE");
        mapLang.put("iw", "HEBREW");
        mapLang.put("hi", "HINDI");
        mapLang.put("hu", "HUNGARIAN");
        mapLang.put("is", "ICELANDIC");
        mapLang.put("id", "INDONESIAN");
        mapLang.put("ga", "IRISH");
        mapLang.put("it", "ITALIAN");
        mapLang.put("ja", "JAPANESE");
        mapLang.put("kn", "KANNADA");
        mapLang.put("ko", "KOREAN");
        mapLang.put("la", "LATIN");
        mapLang.put("lv", "LATVIAN");
        mapLang.put("lt", "LITHUANIAN");
        mapLang.put("mk", "MACEDONIAN");
        mapLang.put("ms", "MALAY");
        mapLang.put("mt", "MALTESE");
        mapLang.put("no", "NORWEGIAN");
        mapLang.put("fa", "PERSIAN");
        mapLang.put("pl", "POLISH");
        mapLang.put("pt", "PORTUGUESE");
        mapLang.put("ro", "ROMANIAN");
        mapLang.put("ru", "RUSSIAN");
        mapLang.put("sr", "SERBIAN");
        mapLang.put("sk", "SLOVAK");
        mapLang.put("sl", "SLOVENIAN");
        mapLang.put("es", "SPANISH");
        mapLang.put("sw", "SWAHILI");
        mapLang.put("sv", "SWEDISH");
        mapLang.put("ta", "TAMIL");
        mapLang.put("te", "TELUGU");
        mapLang.put("th", "THAI");
        mapLang.put("tr", "TURKISH");
        mapLang.put("uk", "UKRAINIAN");
        mapLang.put("ur", "URDU");
        mapLang.put("vi", "VIETNAMESE");
        mapLang.put("cy", "WELSH");
        mapLang.put("yi", "YIDDISH");
    }

    public String getABBRLanguage(String targetLanguage) {
        for (Map.Entry<String, String> entry : mapLang.entrySet()) {
            if (entry.getValue().equals(targetLanguage)) {
                return entry.getKey();
            }
        }
        return null;
    }
}