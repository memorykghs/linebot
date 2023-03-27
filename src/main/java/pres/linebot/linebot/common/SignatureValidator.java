package pres.linebot.linebot.common;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * validate signature - whether message is from line or not
 * 
 * @author memorykghs
 */
@Component
public class SignatureValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignatureValidator.class);

	@Value("${line.bot.channel-secret}")
	private String channelSecret;

	public boolean validateSignature(String requestBody, String signature) {
		try {
			LOGGER.info("===== validating signature =====");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256"));

			// Request body string encode
			String calculatedSignature = Base64.getEncoder().encodeToString(mac.doFinal(requestBody.getBytes()));

			boolean isEqual = calculatedSignature.equals(signature);
			LOGGER.info("=====> validating result: {}", isEqual);

			return isEqual;

		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			LOGGER.error("=====> error validating signature: {}", e.getMessage());
			LOGGER.error("{}", e);
			return false;
		}
	}
}
