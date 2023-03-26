package pres.linebot.linebot.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

@Service
public class FollowEventSvc {

	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FollowEventSvc.class);

	/**
	 * handle follow event
	 * - send Hi sticker
	 * - send default message
	 * 
	 * @param event
	 */
	public void followEventHandler(FollowEvent event) {

		LOGGER.info("===== follow event =====");
		String replyToken = event.getReplyToken();

		// generate sticker message
		StickerMessage stickerMessage = new StickerMessage("11537", "52002750");

		// generate text message
		StringBuilder sb = new StringBuilder();
		TextMessage textMessage1 = generateAmountTrackTextMessage(sb);
		TextMessage textMessage2 = generateDailyAmountTrackTextMessage(sb);

		// wrapped as return message object: ReplyMessage
		List<Message> messages = new ArrayList<>();
		messages.add(stickerMessage);
		messages.add(textMessage1);
		messages.add(textMessage2);
		ReplyMessage replyMessage = new ReplyMessage(replyToken, messages);

		final BotApiResponse botApiResponse;
		try {
			// return message
			botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();
			LOGGER.info("=====> follow event reply: {}", botApiResponse.getMessage());

		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("=====> follow event occurs error: {}", e.getMessage());
			e.printStackTrace();
			return;
		}

		LOGGER.debug("===== follow event reply successed =====");
	}
	
	/**
	 * generate text message
	 * - how to use amount track service
	 * 
	 * @return
	 */
	private TextMessage generateAmountTrackTextMessage(StringBuilder sb) {
		sb.append("哈囉，歡迎加入好友。可以使用'記帳'關鍵字開始每日記帳。\n")
			.append("請使用下方規格：\n")
			.append("日期：yyyyMMdd\n")
			.append("項目： 例：飲料、餐費\n")
			.append("金額：消費金額\n");
		TextMessage textMessage = new TextMessage(sb.toString());
		sb.setLength(0);
		return textMessage;
	}
	
	/**
	 * generate text message
	 * - show daily total expense
	 * 
	 * @return
	 */
	private TextMessage generateDailyAmountTrackTextMessage(StringBuilder sb) {
		sb.append("輸入'當日消費'可以顯示當日總花費金額。\n");
		TextMessage textMessage = new TextMessage(sb.toString());
		sb.setLength(0);
		return textMessage;
	}
}
