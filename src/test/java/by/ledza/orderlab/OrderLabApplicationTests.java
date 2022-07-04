package by.ledza.orderlab;

import by.ledza.orderlab.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderLabApplicationTests {

	@Autowired
	TokenServiceImpl tokenService;

	@Test
	void contextLoads() {
	}

	@Test
	void testEncryptToken(){
		String testString = "This is a test string!";

		String cypherText = tokenService.encryptToken(testString);
		String plainText = tokenService.decryptToken(cypherText);

		assert testString.equals(plainText);
	}

}
