package uz.pdp.g42.bot.strategy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CallBackDataEnums {
    INCREASE_BUTTON("increase"),
    DECREASE_BUTTON("decrease"),
    QUANTITY_BUTTON("quantity"),
    BASKET_BUTTON("basket"),
    NEXT_BUTTON("next"),
    BACK_BUTTON("back");

    private final String data;
}
