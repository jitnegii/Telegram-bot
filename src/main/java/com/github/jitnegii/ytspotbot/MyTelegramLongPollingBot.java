package com.github.jitnegii.ytspotbot;

import com.github.jitnegii.ytspotbot.youtube.Constants;
import com.github.jitnegii.ytspotbot.youtube.models.Format;

import org.apache.commons.io.FileUtils;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MyTelegramLongPollingBot extends TelegramLongPollingBot {

    private final String DOWNLOAD_PATH = System.getProperty("user.dir") + System.getProperty("file.separator") + "downloads" + System.getProperty("file.separator");

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    protected void sendAudio(String chatId, Format format) {

        String path = DOWNLOAD_PATH + System.currentTimeMillis() + "." + format.getExtension();
        File f = getFile(format.getUrl(), path);


        if (f != null) {
            InputFile file = new InputFile(f, format.getTitle() + "." + format.getExtension());

            int duration = (int) (format.getApproxDurationMs() / 1000);

            SendAudio audio = new SendAudio(duration, chatId, file);

            audio.setTitle(format.getTitle());
            audio.setPerformer(format.getAuthor());
            audio.setThumb(new InputFile(format.getThumbnailUrl()));
            try {
                execute(audio);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                sendMsg(chatId, "ERROR!!!");
            }


        } else {
            sendMsg(chatId, "ERROR!!!");
        }


        if (f == null) {
            File file = new File(path);
            if (file.exists())
                file.delete();
        } else {
            if (f.exists())
                f.delete();
        }


    }

    protected void sendMsg(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected File getFile(String url, String path) {

        try {


            File file = new File(path);

            FileUtils.copyURLToFile(new URL(url), file, 10000, 10000);

            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onStart(String chatId, String name) {
        String msg = "Hey " + name + "!\nJust send me url i'll send you a downloadable song";
        sendMsg(chatId, msg);
    }

    protected void onHelp(String chatId) {
        sendMsg(chatId, Constants.HELP);

    }

    protected void onWrongUrl(String chatId) {

        sendMsg(chatId, Constants.WRONG_URL);
    }

    protected void onError(String chatId) {

        sendMsg(chatId, Constants.ERROR);
    }
}
