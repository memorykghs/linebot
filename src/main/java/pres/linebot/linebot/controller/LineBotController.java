package pres.linebot.linebot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pres.linebot.linebot.common.SignatureValidator;
import pres.linebot.linebot.sevice.FollowEventSvc;
import pres.linebot.linebot.sevice.TextMessageSvc;

@RestController
@RequestMapping("/callback")
@Api(tags = "Line Bot")
public class LineBotController {

	@Value("${line.bot.channel-secret}")
	private String CHANNEL_SECRET;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SignatureValidator signatureValidator;

	@Autowired
	private FollowEventSvc followEventSvc;
	
	@Autowired
	private TextMessageSvc textMessageSvc;
	
	@ApiOperation(value = "測試 spring boot api", notes = "如 server 正常則回覆 Test success")
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<>("Test success.", HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "line bot callback")
	public ResponseEntity<String> callback(@RequestBody String requestBody,
			@RequestHeader("X-Line-Signature") String signature) {

		if (!signatureValidator.validateSignature(CHANNEL_SECRET, signature)) {
			throw new RuntimeException("Invalid signature");
		}

		try {
			final JsonNode eventsNode = mapper.readTree(requestBody).get("events");
			if (eventsNode != null && eventsNode.isArray()) {

				// 判斷不同事件
				for (final JsonNode eventNode : eventsNode) {                         
					if (eventNode.has("source") && eventNode.get("source").has("type")) {

						String eventType = eventNode.get("type").asText();
						Event event = mapper.readValue(eventNode.asText(), Event.class);

						switch (eventType) {
						case "follow":
							followEventSvc.followEventHandler((FollowEvent) event);
							break;
						case "message":
							textMessageSvc.textMessageHandler((MessageEvent<TextMessageContent>) event);
							break;
						default:
							break;
						}
					}
				}
			}
			return ResponseEntity.status(HttpStatus.OK).build();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
