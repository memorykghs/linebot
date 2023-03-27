package pres.linebot.linebot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.bot.client.LineMessagingClient;

@Configuration
public class LineMessagingClientConfig {
	
	@Value("${line.bot.channel-secret}")
	private String CHANNEL_SECRET;
	
	@Value("${line.bot.channel-token}")
	private String CHANNEL_TOKEN;

	@Bean
	public LineMessagingClient generateLineMessagingClient() {
		LineMessagingClient client = LineMessagingClient
		        .builder(CHANNEL_TOKEN)
		        .build();
		
		return client;
	}
}
