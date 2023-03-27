package pres.linebot.linebot.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "message")
@Data
public class Message {
	
	public Message() {}

	public Message(String id, String userId, String replyToken, String content, Date createTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.replyToken = replyToken;
		this.content = content;
		this.createTime = createTime;
	}

	@Id
	private String id;
	
	@Field("userId")
	private String userId;
	
	@Field("replyToken")
	private String replyToken;

	@Field("content")
	private String content;
	
	@Field("createTime")
	private Date createTime;
}
