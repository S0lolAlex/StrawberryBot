package org.greenSnake;

import lombok.extern.slf4j.Slf4j;
import org.greenSnake.dto.UserRequest;
import org.greenSnake.dto.UserSession;
import org.greenSnake.dto.UserSessionSaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class StrawberryBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    @Value("${admin.id}")
    private Long adminId;
    private final Dispatcher dispatcher;
    private final AdminDispatcher adminDispatcher;
    private final UserSessionSaver saver;

    public StrawberryBot(Dispatcher dispatcher, UserSessionSaver saver, AdminDispatcher adminDispatcher) {
        this.dispatcher = dispatcher;
        this.saver = saver;
        this.adminDispatcher = adminDispatcher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() || update.hasCallbackQuery()) {
            Long chatId;
            if (update.hasCallbackQuery()) {
                chatId = update.getCallbackQuery().getMessage().getChatId();
            } else {
                chatId = update.getMessage().getChatId();
            }
            UserSession session = saver.getSession(chatId);

            UserRequest userRequest = UserRequest
                    .builder()
                    .update(update)
                    .userSession(session)
                    .chatId(chatId)
                    .build();
            boolean dispatched;
            if (chatId == adminId) {
                dispatched = adminDispatcher.dispatch(userRequest);
            } else {
                dispatched = dispatcher.dispatch(userRequest);
            }
            if (!dispatched) {
                log.debug("User write {}",update.getMessage().getText());
            }
        }
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}