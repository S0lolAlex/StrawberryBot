package org.greenSnake.handler.usersHandlers;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.keyboardSelecter.KeyboardList;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
@Component
public class HelpHandler extends UserRequestHandler {
    private static String command = "/help";

    private final TelegramService telegramService;
    private final KeyboardList keyboard;

    public HelpHandler(TelegramService telegramService, KeyboardList keyboard) {
        this.telegramService = telegramService;
        this.keyboard = keyboard;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        ReplyKeyboard replyKeyboard = keyboard.buildMainMenu();
        telegramService.sendMessage(request.getChatId(),
                "Если бот не реагирует на ваши сообщения выберите из меню ниже",keyboard.buildMainMenu());
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
