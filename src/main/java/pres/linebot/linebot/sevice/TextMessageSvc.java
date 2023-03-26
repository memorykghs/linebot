package pres.linebot.linebot.sevice;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import pres.linebot.linebot.entity.Message;
import pres.linebot.linebot.repository.MessageRepository;

/**
 * handle text message type request - current only has one service - if keyword
 * doesn't mapping, return error message
 * 
 * @author memorykghs
 */
@Service
public class TextMessageSvc {

	private static final Logger LOGGER = LoggerFactory.getLogger(TextMessageSvc.class);

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	/**
	 * determine action according to keyword
	 * 
	 * @param event
	 */
	public void textMessageHandler(MessageEvent<TextMessageContent> event) {
		TextMessageContent textMessage = event.getMessage();
		String keyword = textMessage.getText();
		
		switch (keyword) {
		case "查詢訊息":
			getTextMessages(event);
		default:
			saveTextMessage(event);
		}
	}

	/**
	 * save text message
	 * 
	 * @param event
	 */
	private void saveTextMessage(MessageEvent<TextMessageContent> event) {
		TextMessageContent textMessage = event.getMessage();
		String replyToken = event.getReplyToken();

		try {

			// save message to database
			LOGGER.debug("===== save message =====");

			Message message = new Message();
			message.setReplyToken(replyToken);
			message.setCreateTime(new Timestamp(System.currentTimeMillis()));
			message.setContent(textMessage.getText());
			messageRepository.save(message);

			LOGGER.debug("===== message save success =====");

			// return message
			TextMessage replyTextMessage = new TextMessage("儲存成功");
			ReplyMessage replyMessage = new ReplyMessage(replyToken, replyTextMessage);

			final BotApiResponse botApiResponse;
			botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();

			LOGGER.info("=====> text message event reply: {}", botApiResponse.getMessage());

		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("=====> text message event occurs error: {}", e.getMessage());
			e.printStackTrace();
			return;
		}

		LOGGER.debug("===== text message reply successed =====");
	}

	/**
	 * get all text message
	 * 
	 * @param event
	 */
	private void getTextMessages(MessageEvent<TextMessageContent> event) {
		String replyToken = event.getReplyToken();
		
		try {

			// save message to database
			LOGGER.debug("===== get messages =====");
			List<Message> messages = messageRepository.findByReplyToken(replyToken);
			LOGGER.debug("===== messages get success =====");
			
			// handle messages
			StringBuilder sb = new StringBuilder();
			messages.stream().forEach(e -> sb.append('→').append(". ").append(e.getContent()).append("\n"));
			
			// return message
			TextMessage replyTextMessage = new TextMessage(sb.toString());
			ReplyMessage replyMessage = new ReplyMessage(replyToken, replyTextMessage);

			final BotApiResponse botApiResponse;
			botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();

			LOGGER.info("=====> get text message event reply: {}", botApiResponse.getMessage());

		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("=====> get text message event occurs error: {}", e.getMessage());
			e.printStackTrace();
			return;
		}

		LOGGER.debug("===== get all messages reply successed =====");
	}

}
