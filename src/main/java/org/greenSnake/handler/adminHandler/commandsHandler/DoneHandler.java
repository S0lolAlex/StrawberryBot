package org.greenSnake.handler.adminHandler.commandsHandler;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.entity.Booking;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;


@Component
public class DoneHandler extends AdminRequestHandler {
    private final String DONE = "выполнено";
    private final TelegramService telegramService;
    private final BookingService orders;

    public DoneHandler(TelegramService telegramService,
                       BookingService orders) {
        this.telegramService = telegramService;
        this.orders = orders;
    }
    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(),DONE);
    }

    @Override
    public void handle(UserRequest userRequest) {
        Booking order = orders.getById(Long.parseLong(
                userRequest.getUpdate()
                .getMessage()
                .getText()
                .replace(DONE,"")
                .trim()));
        order.setStatus(true);
        orders.update(order);
        telegramService.sendMessage(userRequest.getChatId(), "Заказ " + order.getId() + " обновлен");
    }
    @Override
    public boolean isGlobal() {
        return false;
    }
}
