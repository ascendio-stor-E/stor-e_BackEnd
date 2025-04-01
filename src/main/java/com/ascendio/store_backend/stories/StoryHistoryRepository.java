package com.ascendio.store_backend.stories;

import com.ascendio.store_backend.chatgpt.ChatGPTHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryHistoryRepository extends JpaRepository<ChatGPTHistory, UUID> {
    default List<ChatGPTHistory> findPreviousMessages(String conversationId){
        return findByConversationIdOrderByCreatedAt(conversationId);
    }

    List<ChatGPTHistory> findByConversationIdOrderByCreatedAt(String conversationId);

    default void saveStory(ChatGPTHistory chatGPTHistory){
        try{
            save(chatGPTHistory);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    List<ChatGPTHistory> findAllByStoryBookId(UUID storyBookId);

}
