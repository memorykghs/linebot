package pres.linebot.linebot.exception;

import lombok.Getter;

@Getter
public class DataNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String requestId, String replyToken, String msg){
		this.requestId = requestId;
		this.replyToken = replyToken;
		this.msg = msg;
	}
	
	private String requestId;
	
	private String msg;
	
	private String replyToken;
}
