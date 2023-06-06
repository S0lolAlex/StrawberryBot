package org.greenSnake.handler.imp;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.keyboardSelecter.KeyboardList;
import org.greenSnake.services.StrawberryService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;

@Component
public class GetAllHandler extends UserRequestHandler {
    private final TelegramService telegramService;
    private static String AVAILABLE = "Что в наличии";
    private final KeyboardList keyboard;
    private final StrawberryService service;

    public GetAllHandler(TelegramService telegramService, KeyboardList keyboard,StrawberryService service) {
        this.telegramService = telegramService;
        this.keyboard = keyboard;
        this.service = service;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isBooking(userRequest.getUpdate(),AVAILABLE);
    }

    @Override
    public void handle(UserRequest userRequest) {
            telegramService.sendMessage(userRequest.getChatId(),"В наличии:", keyboard.buildSortMenu());
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
