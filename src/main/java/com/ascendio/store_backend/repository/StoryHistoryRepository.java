package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.model.ChatGPTHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryHistoryRepository extends JpaRepository<ChatGPTHistory, UUID> {
    default List<ChatGPTHistory> findPreviousMessages(String conversationId){
        return findAllOrderByCreatedAt(conversationId);
    }

    List<ChatGPTHistory> findAllOrderByCreatedAt(String conversationId);

    default void saveStory(ChatGPTHistory chatGPTHistory){
        try{
            save(chatGPTHistory);
        }catch (Exception e){
            System.out.println(e);
        }

    }

}
