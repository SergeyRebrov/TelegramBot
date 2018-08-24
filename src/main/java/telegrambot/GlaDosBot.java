package telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import telegrambot.model.OwnMessage;
import telegrambot.repository.OwnMessageRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class GlaDosBot extends TelegramLongPollingBot {

    private final OwnMessageRepository ownMessageRepository;

    @Autowired
    public GlaDosBot(OwnMessageRepository ownMessageRepository) {
        super();
        this.ownMessageRepository = ownMessageRepository;
    }

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {

                Message message = update.getMessage();

                SendMessage sendMessage = new SendMessage()
                        .setChatId(message.getChatId());

                if (message.getText().equalsIgnoreCase("/start")) {
                    sendMessage.setText(message.getFrom().getFirstName() + ", добро пожаловать!");
                } else if (message.getText().equalsIgnoreCase("/my")) {
                    sendMessage.setText(message.getFrom().getFirstName() + ", список ваших сообщений:");
                    List<OwnMessage> messageList = ownMessageRepository.findByUserId(message.getFrom().getId());

                    if (messageList.isEmpty()) {
                        sendMessage.setText(message.getFrom().getFirstName() + ", список ваших сообщений пуст");
                    }

                    for (int i = 0; i < messageList.size(); i++) {
                        sendMessage.setText(i + 1 + ") " + messageList.get(i).getMessage());
                        if (i + 1 < messageList.size()) {
                            execute(sendMessage);
                        }
                    }
                } else if (message.getText().equalsIgnoreCase("/clean")) {
                    ownMessageRepository.deleteByUserId(message.getFrom().getId());
                    sendMessage.setText(message.getFrom().getFirstName() + ", Ваши сообщения очищены");
                } else {
                    OwnMessage ownMessage = new OwnMessage();
                    ownMessage.setUserId(message.getFrom().getId());
                    ownMessage.setMessage(message.getText());
                    ownMessageRepository.save(ownMessage);
                    sendMessage.setText(message.getFrom().getFirstName() + ", Ваше сообщение записано");
                }

                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "GLaDOS_new_bot";
    }

    @Override
    public String getBotToken() {
        return "568632109:AAHZTTYZc3UpKdDznHE3kDAL77KbuyyfXo4";
    }
}
