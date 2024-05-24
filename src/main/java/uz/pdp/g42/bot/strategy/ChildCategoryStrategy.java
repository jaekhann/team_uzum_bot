package uz.pdp.g42.bot.strategy;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.g42.bot.model.enums.State;
import uz.pdp.g42.bot.service.InlineMarkupService;
import uz.pdp.g42.bot.service.UserService;
import uz.pdp.g42.common.model.Category;
import uz.pdp.g42.common.model.Product;
import uz.pdp.g42.common.service.CategoryService;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class ChildCategoryStrategy extends TelegramBotStrategy {

    private final CategoryService categoryService;
    private final InlineMarkupService<Category> categoryInlineMarkupService;
    private final InlineMarkupService<Product> productInlineMarkupService;
    private final UserService userService;

    @Override
    public State state() {
        return State.CHILD_CATEGORY;
    }

    @Override
    public SendMessage execute(Update update) throws IOException {
        return null;
    }

    @Override
    public EditMessageReplyMarkup execute(CallbackQuery callbackQuery) throws IOException, TelegramApiException {
        String categoryId = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        boolean hasChildCategory = categoryService.hasChildCategory(UUID.fromString(categoryId));
        Integer messageId = callbackQuery.getMessage().getMessageId();
        if (hasChildCategory) {
            return executeJob(chatId,
                    categoryInlineMarkupService.subInlineKeyboardMarkup(UUID.fromString(categoryId), 3),
                    messageId
            );
        }

        userService.setState(chatId, State.PRODUCT);
        return executeJob(chatId,
                productInlineMarkupService.subInlineKeyboardMarkup(UUID.fromString(categoryId), 4),
                messageId
        );
    }
}
