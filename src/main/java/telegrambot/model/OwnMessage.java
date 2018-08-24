package telegrambot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "message")
public class OwnMessage {
    private Long id;
    private Integer userId;
    private String message;

    @Id
    @SequenceGenerator(name = "message_id_seq", sequenceName = "message_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "message", length = -1)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnMessage ownMessage1 = (OwnMessage) o;
        return Objects.equals(id, ownMessage1.id) &&
                Objects.equals(userId, ownMessage1.userId) &&
                Objects.equals(message, ownMessage1.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, message);
    }
}
