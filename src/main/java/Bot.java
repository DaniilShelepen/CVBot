import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi bot = new TelegramBotsApi();
        try {
            bot.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

    void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            keyboard(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendSticker(Message message, String stickerId) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(message.getChatId().toString());
        sendSticker.setSticker(stickerId);

        try {
            sendSticker(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendDoc(Message message, File file) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(message.getChatId().toString());

        sendDocument.setNewDocument(file);
        try {
            sendDocument(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    void sendPicture(Message message, File file) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        sendPhoto.setNewPhoto(file);
        try {
            sendPhoto(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void keyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("CV(rus/eng)"));
        keyboardFirstRow.add(new KeyboardButton("pictures"));
        keyboardSecondRow.add(new KeyboardButton("на бота с фильмами(если сделаешь в итоге)"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    @Override
    public void onUpdateReceived(Update update) {
        final Message message = update.getMessage();

        if (message != null && message.hasText()) {
            switch (update.getMessage().getText()) {

                case "/start" :
                    sendMsg(message, "Привет \n" +
                            "Hello");
                    sendSticker(message, "CAACAgIAAxkBAAEC9W5hTyal0chddwtqO99RjUlCj3JjzwACQhAAAjPFKUmQDtQRpypKgiEE");

                    sendDoc(message, new File("C:\\Users\\danik\\Downloads\\Лабораторная работа №3.doc"));

                    sendPicture(message, new File("C:\\Users\\danik\\OneDrive\\Изображения\\163117717413541755.jpg"));


                case "/help" :
                    sendMsg(message, "напиши типо бот для св");
                    sendSticker(message, "стикерайди");

                default : sendSticker(message, "что нить засунь");
            }
        } else sendMsg(message, "dfghjklo98ytfcvbnjk");
    }

    public String getBotUsername() {
        return "CvTelegramBot";
    }

    public String getBotToken() {
        return "1935333226:AAERM4ec96bNCu018QPvsmZmUIOf0uRMPWI";
    }


}
