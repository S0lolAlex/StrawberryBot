package org.greenSnake.handler.adminHandler;

import org.greenSnake.dto.UserRequest;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AdminRequestHandler {
    public abstract boolean isApplicable(UserRequest request);
    public abstract void handle(UserRequest dispatchRequest);
    public abstract boolean isGlobal();

    public boolean isCommand(Update update, String command) {
        return update.hasMessage() && update.getMessage().isCommand()
                && update.getMessage().getText().contains(command);
    }
    public boolean isTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
