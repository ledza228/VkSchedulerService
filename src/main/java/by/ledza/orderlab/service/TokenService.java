package by.ledza.orderlab.service;

public interface TokenService {

    String encryptToken(String token);

    String decryptToken(String cypherBase64);

}
