package uz.pdp.g42.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.g42.bot.model.User;
import uz.pdp.g42.bot.model.enums.State;
import uz.pdp.g42.bot.service.InlineMarkupService;
import uz.pdp.g42.bot.service.ProductSendPhotoButton;
import uz.pdp.g42.bot.service.UserService;
import uz.pdp.g42.bot.strategy.*;
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
    private static final String BOT_TOKEN = "6811474132:AAG2INR32VKJq8vO4tdeJkeohsRquEOGGDs";

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

    ProductSendPhotoButton productSendPhotoButton = new ProductSendPhotoButton(productDao);

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
        strategyMap.put(State.PRODUCT_ORDER,
                new ProductSendPhotoStrategy(productDao,
                        userService,
                        productSendPhotoButton));
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
                String data = update.getCallbackQuery().getData();
                if (data.length() > 25 && categoryDao.getById(UUID.fromString(data)) != null) {
                    execute(strategyMap.get(State.CHILD_CATEGORY).execute(update.getCallbackQuery()));
                } else {
                    execute(strategyMap.get(State.PRODUCT_ORDER).execute(update.getCallbackQuery(), update.getCallbackQuery().getData()));
                }


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
