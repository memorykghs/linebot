package pres.linebot.linebot.handler;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.error.ErrorResponse;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;
import pres.linebot.linebot.exception.DataNotFoundException;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {

	@Autowired
	private LineMessagingClient lineMessagingClient;

	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseEntity<Object> handleDataNotFoundException(RequestEntity<Object> request, DataNotFoundException dnfe) {
		log.info("===== data not found handler =====");
		TextMessage replyTextMessage = new TextMessage("還沒有文字歐");
		ReplyMessage replyMessage = new ReplyMessage(dnfe.getReplyToken(), replyTextMessage);
		replyMessge(replyMessage);
		
		ErrorResponse errorResponse = new ErrorResponse(dnfe.getRequestId(), "還沒有文字歐", Collections.emptyList());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);

//		try {
//			final BotApiResponse botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();
//			log.info("=====> data not found handler: {}", botApiResponse.getMessage());
//
//		} catch (InterruptedException | ExecutionException e) {
//			log.info("=====> Exception handler: event reply: {}", e.getMessage());
//			e.printStackTrace();
//		}
	}

//	@ExceptionHandler(value = RuntimeException.class)
//	public <T> OrderResponse<T> handleErrorInputException(ErrorInputException eie) {
//		OrderResponse<T> response = new OrderResponse<>();
//		response.setMsg(eie.getMsg());
//		return response;
//	}

	private void replyMessge(ReplyMessage replyMessage) {
		try {
			final BotApiResponse botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();
			log.info("=====> data not found handler: {}", botApiResponse.getMessage());

		} catch (InterruptedException | ExecutionException e) {
			log.info("=====> Exception handler: event reply: {}", e.getMessage());
			e.printStackTrace();
		}
	}
}
