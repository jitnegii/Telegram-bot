package com.github.jitnegii.ytspotbot.youtube.utils;


import java.io.IOException;


import com.github.jitnegii.ytspotbot.Log;
import com.github.jitnegii.ytspotbot.youtube.Constants;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;


public class CipherManager {

    private static  String RegexFindVarCode="";
    public static String cachedDecipherFunction = null;



    public  static String getDecipherCode(String Basejs) {
        String DechipherCode;
        String DecipherFun="decipher=function(a)" + RegexUtils.matchGroup(Constants.REGEX_DECIPHER_FUNCTION, Basejs);

        String RawName=RegexUtils.matchGroup(Constants.REGEX_VAR_NAME, DecipherFun).replace("$","\\$");
        String RealVarName=RawName.split("\\.")[0];
        RegexFindVarCode = "var\\s" + RealVarName + "=.*?\\};";	// Word 1

        String varCode=RegexUtils.matchGroup(RegexFindVarCode, Basejs);
        DechipherCode = DecipherFun + "\n" + varCode;


        return DechipherCode;
    }

    public  static String dechipersig(String sig, String body) throws IOException{
        if(cachedDecipherFunction ==null){

            String jsUrl = RegexUtils.matchGroup(Constants.REGEX_JSURL, body, 2).split("\"")[1];

            if (jsUrl == null) {
                Log.e("JS Url : " , jsUrl);
                return null;
            }

            if (!(jsUrl.startsWith("http") && jsUrl.contains("youtube.com")))
                jsUrl = "https://www.youtube.com" + jsUrl;

            cachedDecipherFunction =getDecipherCode(getPlayerCode(jsUrl));
        }

        return RhinoEngine(sig);
    }

    private static String getPlayerCode(String playerUrl) throws IOException {
        return HTTPUtility.downloadPageSource(playerUrl);
    }


    private static String RhinoEngine(String s) {
        Context rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();
            rhino.evaluateString(scope, cachedDecipherFunction, "JavaScript", 1, null);
            Object obj = scope.get("decipher", scope);


            if (obj instanceof Function) {
                Function jsFunction = (Function) obj;
                Object jsResult = jsFunction.call(rhino, scope, scope, new Object[]{s});
                String result = Context.toString(jsResult);
                return result ;
            }
        }
        finally {
            Context.exit();
        }
        return s;
    }

}
