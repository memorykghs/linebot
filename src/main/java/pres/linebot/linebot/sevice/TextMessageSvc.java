package pres.linebot.linebot.sevice;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;
import pres.linebot.linebot.entity.Message;
import pres.linebot.linebot.exception.DataNotFoundException;
import pres.linebot.linebot.repository.MessageRepository;

/**
 * handle text message type request - current only has one service - if keyword
 * doesn't mapping, return error message
 * 
 * @author memorykghs
 */
@Service
@Slf4j
public class TextMessageSvc {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private LineMessagingClient lineMessagingClient;

	/**
	 * determine action according to keyword
	 * 
	 * @param event
	 * @throws DataNotFoundException
	 */
	public void textMessageHandler(MessageEvent<TextMessageContent> event) throws DataNotFoundException {
		TextMessageContent textMessage = event.getMessage();
		String keyword = textMessage.getText();

		switch (keyword) {
		case "查詢訊息":
			getTextMessages(event);
			break;
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

		// save message to database
		log.debug("===== save message =====");

		Message message = new Message();
		message.setUserId(event.getSource().getUserId());
		message.setReplyToken(replyToken);
		message.setCreateTime(new Timestamp(System.currentTimeMillis()));
		message.setContent(textMessage.getText());
		messageRepository.save(message);

		log.debug("===== message save success =====");

		// return message
		TextMessage replyTextMessage = new TextMessage(textMessage.getText());
		replyMessage(replyToken, replyTextMessage, "save");

		log.debug("===== text message reply successed =====");
	}

	/**
	 * get all text message
	 * 
	 * @param event
	 * @throws DataNotFoundException
	 */
	private void getTextMessages(MessageEvent<TextMessageContent> event) throws DataNotFoundException {
		String replyToken = event.getReplyToken();
		String userId = event.getSource().getUserId();

		// save message to database
		log.debug("===== get messages =====");
		List<Message> messages = messageRepository.findByUserId(userId);

		if (messages == null || messages.isEmpty()) {
			log.debug("=====> data not found");
//			throw new DataNotFoundException(requestId, replyToken, "你還沒有輸入訊息歐~");
			
			// return message
			TextMessage replyTextMessage = new TextMessage("還沒有輸入訊息歐~");
			replyMessage(replyToken, replyTextMessage, "data not found");
			return;
		}

		// handle messages
		StringBuilder sb = new StringBuilder();
		messages.stream().forEach(e -> sb.append('→').append(' ').append(e.getContent()).append("\n"));

		// return message
		TextMessage replyTextMessage = new TextMessage(sb.toString());
		replyMessage(replyToken, replyTextMessage, "query");

		log.debug("===== get all messages reply successed =====");
	}

	/**
	 * handle reply message
	 * 
	 * @param replyToken
	 * @param replyTextMessage
	 * @param action
	 */
	private void replyMessage(String replyToken, TextMessage replyTextMessage, String action) {
		try {
			ReplyMessage replyMessage = new ReplyMessage(replyToken, replyTextMessage);

			final BotApiResponse botApiResponse;
			botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();

			log.info("=====> {} message event reply: {}", action, botApiResponse.getMessage());

		} catch (InterruptedException | ExecutionException e) {
			log.error("=====> {} message event occurs error: {}", action, e.getMessage());
			e.printStackTrace();
			return;
		}
	}
}
