package pres.linebot.linebot.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pres.linebot.linebot.entity.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>{

	/**
	 * get user all messages
	 */
	List<Message> findByUserId(String userId);
	
}
