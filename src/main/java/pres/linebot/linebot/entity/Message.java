package pres.linebot.linebot.entity;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collation = "message")
@Data
public class Message {
	
	public Message() {}

	public Message(String id, String replyToken, String content, Timestamp createTime) {
		super();
		this.id = id;
		this.replyToken = replyToken;
		this.content = content;
		this.createTime = createTime;
	}

	@Id
	private String id;
	
	private String replyToken;

	private String content;
	
	private Timestamp createTime;
}
