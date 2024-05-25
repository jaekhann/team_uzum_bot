package uz.pdp.g42.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.g42.bot.model.User;
import uz.pdp.g42.bot.model.enums.State;
import uz.pdp.g42.bot.service.InlineMarkupService;
import uz.pdp.g42.bot.service.UserService;
import uz.pdp.g42.bot.strategy.CategoryStrategy;
import uz.pdp.g42.bot.strategy.ChildCategoryStrategy;
import uz.pdp.g42.bot.strategy.MainLayoutStrategy;
import uz.pdp.g42.bot.strategy.TelegramBotStrategy;
import uz.pdp.g42.common.dao.CategoryDao;
import uz.pdp.g42.common.dao.ProductDao;
import uz.pdp.g42.common.dao.UserDao;
import uz.pdp.g42.common.model.Category;
import uz.pdp.g42.common.model.Product;
import uz.pdp.g42.common.service.CategoryService;
import uz.pdp.g42.common.service.FileService;
import uz.pdp.g42.common.service.ProductService;

import java.util.*;

public class UzumBot extends TelegramLongPollingBot {
    private static final String USERNAME = "g42_team_uzum_bot";
    private static final String BOT_TOKEN = "6653564571:AAGW0fPeDOHpAml8-XpFkf-lq9NO9aCPfSk";

    FileService<Category> fileService = new FileService<>();
    CategoryDao categoryDao = new CategoryDao(fileService);
    CategoryService categoryService = new CategoryService(categoryDao);
    InlineMarkupService<Category> inlineMarkupService = new InlineMarkupService<>(categoryService);

    FileService<Product> productFileService = new FileService<>();
    ProductDao productDao = new ProductDao(productFileService);
    ProductService productService = new ProductService(productDao);
    InlineMarkupService<Product> productInlineMarkupService = new InlineMarkupService<>(productService);

    FileService<User> userFileService = new FileService<>();
    UserDao userDao = new UserDao(userFileService);
    UserService userService = new UserService(userDao);


    static Map<State, TelegramBotStrategy> strategyMap = new HashMap<>();

    {
        strategyMap.put(State.MAIN, new MainLayoutStrategy(userService));
        strategyMap.put(State.CATEGORY, new CategoryStrategy(userService,
                new InlineMarkupService<>(categoryService)));
        strategyMap.put(State.CHILD_CATEGORY,
                new ChildCategoryStrategy(categoryService,
                        inlineMarkupService,
                        productInlineMarkupService,
                        userService)
        );
    }


    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                String text = update.getMessage().getText();
                switch (text) {
                    case "/start" -> execute(strategyMap.get(State.MAIN).execute(update));

                    case "order" -> execute(strategyMap.get(State.CATEGORY).execute(update));
                }
            }
            else if (update.hasCallbackQuery()) {
                execute(strategyMap.get(State.CHILD_CATEGORY).execute(update.getCallbackQuery()));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }


}
