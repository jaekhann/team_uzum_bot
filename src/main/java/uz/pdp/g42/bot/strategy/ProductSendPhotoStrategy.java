package uz.pdp.g42.bot.strategy;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.g42.bot.model.enums.State;
import uz.pdp.g42.bot.service.ProductSendPhotoButton;
import uz.pdp.g42.bot.service.UserService;
import uz.pdp.g42.common.dao.ProductDao;
import uz.pdp.g42.common.model.Product;

import java.io.IOException;
import java.util.UUID;
@RequiredArgsConstructor
public class ProductSendPhotoStrategy extends TelegramBotStrategy {
    private final ProductDao productDao;
    private final UserService userService;
    private final ProductSendPhotoButton productSendPhotoButton;

    @Override
    public State state() {
        return null;
    }

    @Override
    public SendMessage execute(Update update) throws IOException {
        return null;
    }

    @Override
    public EditMessageReplyMarkup execute(CallbackQuery update) throws IOException {
        return null;
    }

    @Override
    public EditMessageMedia execute(CallbackQuery update, String data) throws IOException {
        Long chatId = update.getMessage().getChatId();
        Integer messageId = update.getMessage().getMessageId();
        userService.setState(chatId, State.PRODUCT_ORDER);

        Product product = null;
        if (data.length() > 25) {
            product = productDao.getById(UUID.fromString(data));
        }
        if (product == null) {
            return null;
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = productSendPhotoButton.getProductPhotoButtons(product);

        return executeJob(chatId, messageId, inlineKeyboardMarkup, product);
    }


}

