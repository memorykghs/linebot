package pres.linebot.linebot.sevice;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherInfoSvc {

	@Value("$weather.api.key}")
	private static String WEATHER_API_KEY;

	/* 氣象局 url */
	private static String API_URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization="
			+ WEATHER_API_KEY + "&locationName=";

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherInfoSvc.class);

//	private void handleUserTextMessage(Map<String, Object> event) {
//		String text = (String) event.get("message").get("text");
//		switch (text) {
//		case "天氣":
//			String weather = getWeather();
//			replyTextMessage((String) event.get("replyToken"), weather);
//			break;
//		// 其他關鍵字處理
//		default:
//			log.info("Unknown keyword: {}", text);
//			break;
//		}
//	}
//
//	private String getWeather() {
//	    try {
//	    	WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);
//	    }catch() {
//	    }
//	}
}
