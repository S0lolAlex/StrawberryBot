package org.greenSnake.handler.usersHandlers;

import lombok.extern.slf4j.Slf4j;
import org.greenSnake.dto.UserRequest;
import org.greenSnake.dto.UserSession;
import org.greenSnake.dto.UserSessionSaver;
import org.greenSnake.entity.Booking;
import org.greenSnake.entity.Client;
import org.greenSnake.enums.ConversationState;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.ClientService;
import org.greenSnake.services.StrawberryService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class SortHandler extends UserRequestHandler {
    private final TelegramService telegramService;
    private final StrawberryService service;
    private final ClientService client;
    private final UserSessionSaver saver;
    private final BookingService orders;

    public SortHandler(TelegramService telegramService,StrawberryService service
    ,ClientService client,UserSessionSaver saver,BookingService orders) {
        this.telegramService = telegramService;
        this.service = service;
        this.client = client;
        this.saver = saver;
        this.orders = orders;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return userRequest.getUpdate().hasCallbackQuery() &&
                userRequest.getUserSession().getState().equals(ConversationState.WAITING_FOR_SORT);
    }

    @Override
    public void handle(UserRequest userRequest) {
        log.info("user set count kg");
        Client user = client.getById(userRequest.getChatId());
        String text = userRequest.getUpdate().getCallbackQuery().getData();
        if(user!=null){
            Booking order = orders.getLastOrder(user.getId());
            order.setSort(text);
            orders.update(order);
            UserSession userSession = userRequest.getUserSession();
            userSession.setState(ConversationState.WAITING_FOR_COUNT);
            saver.saveSession(userSession.getChatId(), userSession);
            telegramService.sendMessage(userRequest.getChatId(),"Сколько кустов вы хотите заказать?");
        }
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
