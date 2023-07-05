package com.example.troknite.troknite.repos;

import com.example.troknite.troknite.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
