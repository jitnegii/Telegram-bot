package com.github.jitnegii.ytspotbot.youtube;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final  String REGEX_YTURL = "(?:https?:\\/\\/)?(?:youtu\\.be\\/|(?:www\\.|m\\.)?youtube\\.com\\/(?:watch|v|embed)(?:\\.php)?(?:\\?.*v=|\\/))([a-zA-Z0-9_-]+)";

    public static final String REGEX_FIND_REASON = "(?<=(class=\"message\">)).*?(?=<)";
    public static final String REGEX_PLAYER_JSON = "(var\\sytInitialPlayerResponse\\s*=)(.*?((\\}(\\n|)\\}(\\n|))|(\\}))(?=;))";
    public static final List<String> REASON_AVAILABLE = Arrays.asList(new String[]{"This video is unavailable on this device.", "Content Warning", "who has blocked it on copyright grounds."});

    public static final String REGEX_VAR_NAME="[a-zA-Z0-9$]{2}\\.[a-zA-Z0-9$]{2}\\([a-zA-Z]\\,(\\d\\d|\\d)\\)";
    public static final String REGEX_DECIPHER_FUNCTION ="\\{[a-zA-Z]{1,}=[a-zA-Z]{1,}.split\\(\"\"\\);[a-zA-Z0-9$]{2}\\.[a-zA-Z0-9$]{2}.*?[a-zA-Z]{1,}.join\\(\"\"\\)\\};";

    public static final String REGEX_JSURL = "(\"jsUrl\"\\s*:)(.*?)(?=,)";
    public static final String jsUrl = "https://www.youtube.com/s/player/2e6e57d8/player_ias.vflset/en_US/base.js";

    public static final String WRONG_URL = "Check url again!!!\nNeed help? just click on /help";
    public static final String HELP = "Hey! i'm your YtSpot bot. I thing you need some help.\nWe accept only spotify and youtube url so i'll send error message on other texts.";
    public static final String ERROR = "Oops! something went wrong";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36";

}
