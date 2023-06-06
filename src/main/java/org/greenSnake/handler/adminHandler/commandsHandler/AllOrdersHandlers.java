package org.greenSnake.handler.adminHandler.commandsHandler;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.entity.Booking;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllOrdersHandlers extends AdminRequestHandler {
    private final String ALL_ORDERS = "Все";
    private final TelegramService telegramService;
    private final BookingService service;

    public AllOrdersHandlers(TelegramService telegramService, BookingService service) {
        this.telegramService = telegramService;
        this.service = service;
    }
    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate()) && request.getUpdate().getMessage().getText().equals(ALL_ORDERS);
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        List<Booking> list = service.getAll().stream().filter(u -> !u.isStatus()).toList();
        telegramService.sendMessage(dispatchRequest.getChatId(), list.toString());
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
