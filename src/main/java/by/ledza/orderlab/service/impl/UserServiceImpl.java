package by.ledza.orderlab.service.impl;

import by.ledza.orderlab.exceptions.VkTokenException;
import by.ledza.orderlab.exceptions.UserNotFoundedException;
import by.ledza.orderlab.model.UserAuthToken;
import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.repository.UserCredsRepository;
import by.ledza.orderlab.service.UserService;
import by.ledza.orderlab.vk.VkUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserCredsRepository userRepository;

    @Autowired
    TokenServiceImpl tokenService;

    public void save(UserCreds user){
        userRepository.save(user);
    }

    public List<UserCreds> getAllUsers(){
        return userRepository.findAll();
    }

    public UserCreds getUser(String id){
        return userRepository.findById(id).orElse(null);
    }

    public void editToken(String id, String token){
        UserCreds user = getUser(id);
        if (user == null)
            throw new UserNotFoundedException();

        String encryptedToken = tokenService.encryptToken(token);

        UserAuthToken authToken = new UserAuthToken(id, encryptedToken);
        user.setVk_token(authToken);
        save(user);
    }

    public String getToken(String id){
        UserCreds user = getUser(id);
        if (user == null)
            throw new UserNotFoundedException();

        String encryptedToken = user.getVk_token().getAuthToken();
        return tokenService.decryptToken(encryptedToken);
    }

    public VkUser getVkUser(String userId){
        UserCreds user = getUser(userId);

        if (user == null)
            throw new UserNotFoundedException();

        if (user.getVk_token() == null)
            throw new VkTokenException();

        if (user.getVk_token().getAuthToken() == null)
            throw new VkTokenException("Problem with vk token");

        return new VkUser(tokenService.decryptToken(user.getVk_token().getAuthToken()));
    }
}
