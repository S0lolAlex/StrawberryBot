package org.greenSnake.handler.imp;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.keyboardSelecter.KeyboardList;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class StartCommandHandler extends UserRequestHandler {
    private static String command = "/start";

    private final TelegramService telegramService;
    private final KeyboardList keyboard;

    public StartCommandHandler(TelegramService telegramService, KeyboardList keyboard) {
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
                "\uD83D\uDC4BПривет, с помощью этого бота вы можете сделать заказ или посмотреть статус заказа",
                replyKeyboard);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
