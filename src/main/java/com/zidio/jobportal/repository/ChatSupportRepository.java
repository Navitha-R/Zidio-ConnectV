package com.zidio.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zidio.jobportal.entity.ChatSupport;

public interface ChatSupportRepository extends JpaRepository<ChatSupport,Long>{
	 @Query("""
	            SELECT c FROM ChatSupport c 
	            WHERE (c.senderId = :user1 AND c.receiverId = :user2)
	               OR (c.senderId = :user2 AND c.receiverId = :user1)
	            ORDER BY c.timeStamp ASC
	            """)
	    List<ChatSupport> findChatHistory(String user1, String user2);

	

}
