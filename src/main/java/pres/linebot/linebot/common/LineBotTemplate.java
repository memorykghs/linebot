package pres.linebot.linebot.common;

import org.springframework.stereotype.Component;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

/**
 * Line Bot Template
 * 
 * @author memorykghs
 */
@Component
@LineMessageHandler
public class LineBotTemplate {
	
	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		String userId = event.getSource().getUserId();
		String replyToken = event.getReplyToken();
		String text = event.getMessage().getText();
		System.out.println("User ID: " + userId);
		System.out.println("Reply Token: " + replyToken);
		System.out.println("Message: " + text);
		return new TextMessage("Hello, " + text);
	}
}
