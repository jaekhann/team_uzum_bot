package uz.pdp.g42.bot.strategy;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.g42.bot.model.enums.State;
import uz.pdp.g42.bot.service.InlineMarkupService;
import uz.pdp.g42.bot.service.UserService;
import uz.pdp.g42.common.model.Category;

import java.io.IOException;

@RequiredArgsConstructor
public class CategoryStrategy extends TelegramBotStrategy {

    private final UserService userService;
    private final InlineMarkupService<Category> categoryInlineMarkupService;

    @Override
    public State state() {
        return State.CATEGORY;
    }

    @Override
    public SendMessage execute(Update update) throws IOException {
        Long chatId = update.getMessage().getChatId();
        userService.setState(chatId, State.CATEGORY);
        return executeJob(chatId, "choose category",
                categoryInlineMarkupService.mainInlineKeyboardMarkup(3));
    }

    @Override
    public EditMessageReplyMarkup execute(CallbackQuery update) throws IOException {
        return null;
    }
}
