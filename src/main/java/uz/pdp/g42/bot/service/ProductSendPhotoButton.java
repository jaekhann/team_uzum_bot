package uz.pdp.g42.bot.service;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.g42.bot.strategy.enums.CallBackDataEnums;
import uz.pdp.g42.common.dao.ProductDao;
import uz.pdp.g42.common.model.Product;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductSendPhotoButton {
    private final ProductDao productDao;

    public InlineKeyboardMarkup getProductPhotoButtons(Product product) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboard = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(inlineKeyboard);

        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        if (product.getQuantity() > 0) {
            InlineKeyboardButton decreaseButton = new InlineKeyboardButton("-");
            decreaseButton.setCallbackData(CallBackDataEnums.DECREASE_BUTTON.getData());
        }
        InlineKeyboardButton quantityButton = new InlineKeyboardButton(String.valueOf(product.getQuantity()));
        quantityButton.setCallbackData(CallBackDataEnums.QUANTITY_BUTTON.getData());
        buttonList.add(quantityButton);
        InlineKeyboardButton increaseButton = new InlineKeyboardButton("+");
        increaseButton.setCallbackData(CallBackDataEnums.INCREASE_BUTTON.getData());
        buttonList.add(increaseButton);
        inlineKeyboard.add(buttonList);
        buttonList = new ArrayList<>();

        InlineKeyboardButton basketButton = new InlineKeyboardButton("basket");
        basketButton.setCallbackData(CallBackDataEnums.BASKET_BUTTON.getData());
        buttonList.add(basketButton);
        inlineKeyboard.add(buttonList);

        buttonList = new ArrayList<>();
        InlineKeyboardButton backButton = new InlineKeyboardButton("back");
        backButton.setCallbackData(CallBackDataEnums.BACK_BUTTON.getData());
        buttonList.add(backButton);
        inlineKeyboard.add(buttonList);

        return inlineKeyboardMarkup;
    }
}
