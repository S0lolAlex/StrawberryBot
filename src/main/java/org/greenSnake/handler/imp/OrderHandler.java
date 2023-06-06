package org.greenSnake.handler.imp;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.dto.UserSession;
import org.greenSnake.dto.UserSessionSaver;
import org.greenSnake.entity.Booking;
import org.greenSnake.enums.ConversationState;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.ClientService;
import org.greenSnake.services.TelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderHandler extends UserRequestHandler {
    private static String ORDER = "Статус заказа";
    private final TelegramService telegramService;
    private final ClientService service;
    private final UserSessionSaver saver;
    private final BookingService orders;
    @Value("${admin.id}")
    private Long adminId;

    public OrderHandler(TelegramService telegramService,ClientService service,
                        UserSessionSaver saver,BookingService orders) {
        this.telegramService = telegramService;
        this.service = service;
        this.saver = saver;
        this.orders = orders;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        boolean isPhone = false;
        boolean isBooking = false;
        if (isTextMessage(userRequest.getUpdate())) {
            String text = userRequest.getUpdate().getMessage().getText();
            ConversationState state = userRequest.getUserSession().getState();
            isPhone = text.matches("(0\\d{9})") && state.equals(ConversationState.WAITING_FOR_PHONE);
            isBooking = text.matches("^[1-9]{1,3}0?$") && state.equals(ConversationState.WAITING_FOR_COUNT);
        }
        return isBooking(userRequest.getUpdate(), ORDER) || isBooking || isPhone;
    }

    @Override
    public void handle(UserRequest userRequest) {
        ConversationState state = userRequest.getUserSession().getState();
        String text = userRequest.getUpdate().getMessage().getText();
        Long userId = userRequest.getUpdate().getMessage().getFrom().getId();
        Booking order = orders.getLastOrder(userId);
        if(isBooking(userRequest.getUpdate(),ORDER) && !orders.isEmpty(userId)){
            List<Booking> list = orders.getAllById(userId);
            telegramService.sendMessage(userRequest.getChatId(),"Вы заказали : \n" + list.toString());
        }
        if(state.equals(ConversationState.WAITING_FOR_COUNT)){
            int bushCount = Integer.parseInt(text);
            order.setBushCount(bushCount);
            orders.update(order);
            UserSession userSession = userRequest.getUserSession();
            userSession.setState(ConversationState.WAITING_FOR_PHONE);
            saver.saveSession(userSession.getChatId(), userSession);
            telegramService.sendMessage(userRequest.getChatId(),
                    "Напишите свой телефона для связи без кода страны");
        }
        if(state.equals(ConversationState.WAITING_FOR_PHONE)){
            int phone = Integer.parseInt(text);
            order.setPhone(phone);
            orders.update(order);
            UserSession userSession = userRequest.getUserSession();
            userSession.setState(ConversationState.WAIT_ORDER);
            saver.saveSession(userSession.getChatId(), userSession);
            telegramService.sendMessage(userRequest.getChatId(),
                    "Заказ принят.\nЖдите звонка для уточнения данных.");
//            telegramService.sendMessage(adminId, service.getById(userRequest.getChatId()).getName()
//            + " хочет заказать " + order + "Телефон для связи: " + order.getPhone());
        }
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
