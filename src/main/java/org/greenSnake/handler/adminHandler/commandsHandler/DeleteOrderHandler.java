package org.greenSnake.handler.adminHandler.commandsHandler;

import lombok.extern.slf4j.Slf4j;
import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class DeleteOrderHandler extends AdminRequestHandler {
    private String command = "удалить заказ";
    private final TelegramService telegramService;
    private final BookingService orders;

    public DeleteOrderHandler(TelegramService telegramService, BookingService orders) {
        this.telegramService = telegramService;
        this.orders = orders;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        log.info("delete order");
        Long orderId = Long.parseLong(dispatchRequest
                .getUpdate()
                .getMessage()
                .getText()
                .replace(command, "")
                .trim());
        orders.delete(orders.getById(orderId));
        telegramService.sendMessage(dispatchRequest.getChatId(),"Заказ удален");
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
