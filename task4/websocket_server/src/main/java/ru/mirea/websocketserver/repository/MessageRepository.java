package ru.mirea.websocketserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.websocketserver.model.Message;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByCreatedDtIsBeforeOrderByCreatedDt(LocalDateTime createdDt);

}
