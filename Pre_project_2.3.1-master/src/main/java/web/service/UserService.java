package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<User> getAllUsers();

    void updateUser(User editedUser, int id);

    User getUserById(int id);

    void removeUserById(int id);
}
