package by.ledza.orderlab.service.impl;

import by.ledza.orderlab.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret}")
    private String secret;

    public String encryptToken(String token) {

        try {
            byte[] keyData = secret.getBytes();

            SecretKeySpec spec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, spec);

            byte[] tokenEncoded = cipher.doFinal(token.getBytes());

            return new String(Base64.getEncoder().encode(tokenEncoded));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public String decryptToken(String cypherBase64){

        byte[] cypher = Base64.getDecoder().decode(cypherBase64);

        try {
            byte[] keyData = secret.getBytes();

            SecretKeySpec spec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, spec);

            byte[] tokenEncoded = cipher.doFinal(cypher);

            return new String(tokenEncoded);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
