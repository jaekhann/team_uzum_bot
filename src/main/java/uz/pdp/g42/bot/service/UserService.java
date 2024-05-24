package uz.pdp.g42.bot.service;

import lombok.RequiredArgsConstructor;
import uz.pdp.g42.bot.model.User;
import uz.pdp.g42.bot.model.enums.State;
import uz.pdp.g42.common.dao.UserDao;

import java.io.IOException;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    public void setState(long chatId, State state) throws IOException {
        User user = User.builder()
                .chatId(chatId)
                .state(state)
                .build();
        userDao.update(user);
    }
}
