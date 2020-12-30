package com.github.jitnegii.ytspotbot;

import com.github.jitnegii.ytspotbot.youtube.Constants;
import com.github.jitnegii.ytspotbot.youtube.Youtubedl;
import com.github.jitnegii.ytspotbot.youtube.models.Format;
import com.github.jitnegii.ytspotbot.youtube.request.ExtractorRequest;
import com.github.jitnegii.ytspotbot.youtube.request.onCompleteListener;
import com.github.jitnegii.ytspotbot.youtube.utils.RegexUtils;
import com.github.jitnegii.ytspotbot.youtube.utils.SpotifyUtils;
import com.github.jitnegii.ytspotbot.youtube.utils.YTutils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.ArrayList;

@Component
@Controller
public class BotController extends MyTelegramLongPollingBot {


    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.name}")
    private String botName;

    @Override
    public String getBotUsername() {

        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {
        Log.print("Registered");
        Log.print(botToken);
    }

    public BotController() {

    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        String senderText = update.getMessage().getText().trim();
        String sender = update.getMessage().getChat().getFirstName();

        if (senderText.equals("/start")) {
            onStart(chatId, sender);
            return;
        }

        if (senderText.equals("/help")) {
            onHelp(chatId);
            return;
        }

        Log.print("\nMessageReceived : " + senderText + "\n");

        if (senderText.contains("open.spotify.com/track/")) {

            senderText = SpotifyUtils.parseUrl(senderText);

            if (senderText == null)
                onWrongUrl(chatId);
            else
                response(chatId, senderText, false);

        } else if (RegexUtils.hasMatch(Constants.REGEX_YTURL, senderText)) {

            response(chatId, senderText, false);

        } else {
            onWrongUrl(chatId);
        }

    }

    private Format response(String chatId, String link, boolean returnOnComplete) {


        if (!returnOnComplete)
            sendMsg(chatId, "Processing...");

        final Format[] format = {null};

        ExtractorRequest request = Youtubedl.execute(link).start(new onCompleteListener() {
            @Override
            public void onFailure(Error error) {
                Log.print(error.getMessage());
                onError(chatId);
            }

            @Override
            public void onComplete(ArrayList<Format> formats) {


                if (returnOnComplete) {

                    for (Format f : formats){
                        if(f.isAudio()){
                            if(f.getMimeType().contains("audio/m4a"))
                                format[0] = f;
                        }
                    }

                    if(format[0]==null && formats.size()>0)
                        format[0] = formats.get(0);

                    return;
                }

                long small = Long.MAX_VALUE;

                for (Format f : formats) {
                    if (f.isAudio()) {
                        long length = (long) f.getContentLength();

                        if (length < small) {
                            small = length;
                            Log.print("Size : " + YTutils.getSize(length));
                            format[0] = f;
                        }

                    }
                }



                if (format[0] != null) {
                    sendAudio(chatId, format[0]);
                } else {
                    onError(chatId);
                }

                Log.print("\nCompleted !!!\n");
            }
        });

        if (returnOnComplete) {
            while (!request.getFuture().isDone()) {
            }
        }

        return format[0];
    }

    @RequestMapping("/")
    public String home() {
        return "home.jsp";
    }

    @RequestMapping("/showAudio")
    public ModelAndView showAudio(@RequestParam String url) {

        ModelAndView mv = new ModelAndView("showAudio.jsp");
        String src;
        Format format = null;
        String msg;
        String title;

        if (url.contains("open.spotify.com/track/"))
            url = SpotifyUtils.parseUrl(url);

        if (!(url == null || url.length() == 0))
            format = response(null, url, true);

        if (format != null) {
            msg = "";
            src = format.getUrl();
            title = format.getTitle();
        } else {
            msg = "Oops! Something went wrong";
            title = "Error";
            src = "";
        }

        Log.print("src " +src);
        mv.addObject("src", src);
        mv.addObject("msg", msg);
        mv.addObject("title", title);

        return mv;
    }

}
