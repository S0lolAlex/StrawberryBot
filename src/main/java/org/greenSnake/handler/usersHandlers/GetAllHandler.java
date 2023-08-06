package org.greenSnake.handler.usersHandlers;

import lombok.extern.slf4j.Slf4j;
import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.keyboardSelecter.KeyboardList;
import org.greenSnake.services.StrawberryService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
@Slf4j
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
        log.info("user take available, state {}",userRequest.getUserSession().getState());
            telegramService.sendMessage(userRequest.getChatId(),"В наличии:", keyboard.buildSortMenu());
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
