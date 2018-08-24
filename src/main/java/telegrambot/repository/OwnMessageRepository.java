package telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import telegrambot.model.OwnMessage;

import java.util.List;

public interface OwnMessageRepository extends JpaRepository<OwnMessage, Long> {

    @Transactional
    void deleteByUserId(Integer userId);

    List<OwnMessage> findByUserId(Integer userId);
}
