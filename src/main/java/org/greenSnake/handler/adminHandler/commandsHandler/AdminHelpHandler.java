package org.greenSnake.handler.adminHandler.commandsHandler;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.greenSnake.keyboardSelecter.KeyboardList;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class AdminHelpHandler extends AdminRequestHandler {
    private static String command = "/help";

    private final TelegramService telegramService;

    public AdminHelpHandler(TelegramService telegramService, KeyboardList keyboard) {
        this.telegramService = telegramService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        telegramService.sendMessage(request.getChatId(),
                "вы можете выполнить следующие операции введя в чат:\n" +
                        "<номер заказа> удалить заказ - удаляет заказ\n" + //+
                        "<номер телефона клиента> удалить клиента - удаляет клиента по номеру\n" + //+
                        "команда \"Все\" выводит список заказов которые не выполнены \n" + //+
                        "<номер заказа> выполнен - изменяет статус заказа на выполнен \n" + //+
                        "<id клиента> заказы - выводит все заказы по id клиента");//+
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
