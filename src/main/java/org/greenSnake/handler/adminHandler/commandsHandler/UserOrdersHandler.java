package org.greenSnake.handler.adminHandler.commandsHandler;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.entity.Booking;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserOrdersHandler extends AdminRequestHandler {
    private String ALL_ORDERS = "заказы";
    private final TelegramService telegramService;
    private final BookingService service;

    public UserOrdersHandler(TelegramService telegramService, BookingService service) {
        this.telegramService = telegramService;
        this.service = service;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(),ALL_ORDERS);
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long id = Long.parseLong(
                userRequest.getUpdate()
                        .getMessage()
                        .getText()
                        .replace(ALL_ORDERS,"")
                        .trim());
        List<Booking> allById = service.getAllById(id);
        telegramService.sendMessage(userRequest.getChatId(),allById.toString());
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
