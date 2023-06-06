package org.greenSnake.handler.imp;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.dto.UserSession;
import org.greenSnake.dto.UserSessionSaver;
import org.greenSnake.entity.Booking;
import org.greenSnake.entity.Client;
import org.greenSnake.enums.ConversationState;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.keyboardSelecter.KeyboardList;
import org.greenSnake.services.BookingService;
import org.greenSnake.services.ClientService;
import org.greenSnake.services.TelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class BookingHandler extends UserRequestHandler {
    public static String BOOKING = "Сделать заказ";

    private final TelegramService telegramService;
    private final KeyboardList keyboard;
    private final UserSessionSaver saver;
    private final ClientService client;
    private final BookingService orders;

    public BookingHandler(TelegramService telegramService, KeyboardList keyboard,UserSessionSaver saver,
                          ClientService client,BookingService orders) {
        this.telegramService = telegramService;
        this.keyboard = keyboard;
        this.saver = saver;
        this.client = client;
        this.orders = orders;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isBooking(userRequest.getUpdate(), BOOKING);
    }

    @Override
    public void handle(UserRequest userRequest) {
        Client user;
        if(client.getById(userRequest.getUpdate().getMessage().getFrom().getId())==null){
            user = new Client();
            user.setId(userRequest.getUpdate().getMessage().getFrom().getId());
            user.setChatId(userRequest.getChatId());
            user.setName(userRequest.getUpdate().getMessage().getFrom().getUserName());
            client.add(user);
        }else{
            user = client.getById(userRequest.getUpdate().getMessage().getFrom().getId());
        }
        Booking order = new Booking();
        order.setClient(user);
        order.setPhone(0);
        order.setBushCount(0);
        orders.add(order);
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.buildSortMenu();
        telegramService.sendMessage(userRequest.getChatId(),"Выберите сорт⤵️",inlineKeyboardMarkup);

        UserSession userSession = userRequest.getUserSession();
        userSession.setState(ConversationState.WAITING_FOR_SORT);
        saver.saveSession(userSession.getChatId(), userSession);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
