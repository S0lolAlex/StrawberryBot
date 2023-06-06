package org.greenSnake.handler.adminHandler.commandsHandler;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.entity.Booking;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllByPhoneHandler extends AdminRequestHandler {
    private final TelegramService telegramService;
    private final BookingService service;

    public GetAllByPhoneHandler(TelegramService telegramService, BookingService service) {
        this.telegramService = telegramService;
        this.service = service;
    }
    @Override
    public boolean isApplicable(UserRequest request) {
        String REGEX = "^[1-9]{1,3}0?$";
        return isTextMessage(request.getUpdate()) && request.getUpdate().getMessage().getText().matches(REGEX);
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        Long phone = Long.parseLong(dispatchRequest.getUpdate().getMessage().getText());
        List<Booking> list = service.getAll().stream().filter(u -> u.getPhone()==phone).toList();
        telegramService.sendMessage(dispatchRequest.getChatId(), list.toString());
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
