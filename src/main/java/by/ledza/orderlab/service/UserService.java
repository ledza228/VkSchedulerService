package by.ledza.orderlab.service;

import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.vk.VkUser;

import java.util.List;

public interface UserService {

    void save(UserCreds user);

    List<UserCreds> getAllUsers();

    UserCreds getUser(String id);

    void editToken(String id, String token);

    String getToken(String id);



}
