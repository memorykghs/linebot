package pres.linebot.linebot.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FollowEventSvc {

	@Autowired
	private LineMessagingClient lineMessagingClient;
	

	/**
	 * handle follow event
	 * - send Hi sticker
	 * - send default message
	 * 
	 * @param event
	 */
	public void followEventHandler(FollowEvent event) {

		log.info("===== follow event =====");
		String replyToken = event.getReplyToken();

		// generate sticker message
		StickerMessage stickerMessage = new StickerMessage("11538", "51626494");

		// generate text message
		StringBuilder sb = new StringBuilder();
		TextMessage textMessage1 = generateFollowEventMessage(sb);

		// wrapped as return message object: ReplyMessage
		List<Message> messages = new ArrayList<>();
		messages.add(stickerMessage);
		messages.add(textMessage1);
		ReplyMessage replyMessage = new ReplyMessage(replyToken, messages);

		final BotApiResponse botApiResponse;
		try {
			// return message
			botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();
			log.info("=====> follow event reply: {}", botApiResponse.getMessage());

		} catch (InterruptedException | ExecutionException e) {
			log.error("=====> follow event occurs error: {}", e.getMessage());
			e.printStackTrace();
			return;
		}

		log.debug("===== follow event reply successed =====");
	}
	
	/**
	 * generate text message
	 * - how to use amount track service
	 * 
	 * @return
	 */
	private TextMessage generateFollowEventMessage(StringBuilder sb) {
		sb.append("哈囉，歡迎加入好友。\n").append("可以使用'查詢訊息'關鍵字查詢已送出的訊息。");
		TextMessage textMessage = new TextMessage(sb.toString());
		sb.setLength(0);
		return textMessage;
	}
}
