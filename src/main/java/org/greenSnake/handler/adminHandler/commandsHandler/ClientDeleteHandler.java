package org.greenSnake.handler.adminHandler.commandsHandler;

import lombok.extern.slf4j.Slf4j;
import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.ClientService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class ClientDeleteHandler extends AdminRequestHandler {
    private String command = "удалить клиента";
    private ClientService client;
    private TelegramService service;
    private BookingService orders;

    public ClientDeleteHandler(TelegramService service, ClientService client,BookingService orders) {
        this.client = client;
        this.service = service;
        this.orders = orders;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest dispatchRequest) {
        log.info("Client delete by phone");
        Long userId = Long.parseLong(
                dispatchRequest
                        .getUpdate()
                        .getMessage()
                        .getText()
                        .replace(command, "")
                        .trim());
        client.delete(client.getById(orders.getClientByPhone(userId)));
        service.sendMessage(dispatchRequest.getChatId(),"Клиент удален");
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
