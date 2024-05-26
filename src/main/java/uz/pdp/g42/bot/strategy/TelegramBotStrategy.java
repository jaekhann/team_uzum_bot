package uz.pdp.g42.bot.strategy;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.g42.bot.model.enums.State;
import uz.pdp.g42.common.model.Product;

import java.beans.JavaBean;
import java.io.IOException;

public abstract class TelegramBotStrategy {
    public abstract State state();
    public abstract SendMessage execute(Update update) throws IOException;
    public abstract EditMessageReplyMarkup execute(CallbackQuery update) throws IOException, TelegramApiException;
    public abstract EditMessageMedia execute(CallbackQuery update, String data) throws IOException;

    protected SendMessage executeJob(Long chatId, String text, ReplyKeyboard r) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
        sendMessage.setReplyMarkup(r);

        return sendMessage;
    }

    protected EditMessageReplyMarkup executeJob(Long chatId, InlineKeyboardMarkup r, int messageId) throws TelegramApiException {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setReplyMarkup(r);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setChatId(chatId);
        return editMessageReplyMarkup;
    }

    protected EditMessageMedia executeJob(Long chatId, int messageId, InlineKeyboardMarkup inlineKeyboardMarkup, Product product) {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMessageId(messageId);

        InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
        inputMediaPhoto.setMedia(product.getUrlPhoto());
        inputMediaPhoto.setCaption(product.getName() + " " + product.getPrice() + "$");

        editMessageMedia.setMedia(inputMediaPhoto);
        editMessageMedia.setReplyMarkup(inlineKeyboardMarkup);

        return editMessageMedia;
    }
}
