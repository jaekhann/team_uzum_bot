package uz.pdp.g42.common.dao;

import lombok.RequiredArgsConstructor;
import uz.pdp.g42.bot.model.User;
import uz.pdp.g42.common.dao.enums.FilePath;
import uz.pdp.g42.common.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserDao implements BaseDao<User> {
    private final FileService<User> fileService;
    @Override
    public void create(User user) throws IOException {
        fileService.create(user, FilePath.USER, User[].class);
    }

    @Override
    public User getById(UUID id) {
        return null;
    }

    @Override
    public List<User> list() throws IOException {
        return fileService.read(FilePath.USER, User[].class);
    }

    public void update(User user) throws IOException {
        // TODO please optimize this since I don't have time

        List<User> users = list();
        Optional<User> optionalUser = getUser(users, user.getChatId());
        if (optionalUser.isEmpty()) {
            fileService.create(user, FilePath.USER, User[].class);
        }else {
            users.forEach(user1 -> {
                if (user1.getChatId() == user.getChatId()) {
                    user1.setState(user.getState());
                }
            });

            fileService.update(users, FilePath.USER);
        }
    }

    private Optional<User> getUser(List<User> users, long chatId) {
        return users.stream()
                .filter(user -> user.getChatId() == chatId)
                .findFirst();
    }

}
