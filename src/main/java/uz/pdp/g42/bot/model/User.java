package uz.pdp.g42.bot.model;

import lombok.Builder;
import lombok.Data;
import uz.pdp.g42.bot.model.enums.State;

@Data
@Builder
public class User {
    private long chatId;
    private State state;
}
