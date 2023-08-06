package org.greenSnake.handler.usersHandlers;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class BookingHandler extends UserRequestHandler {
    public static String BOOKING = "Сделать заказ";

    private final TelegramService telegramService;
    private final InlineKeyboardMarkup keyboard = new KeyboardList().buildSortMenu();
    private final UserSessionSaver saver;
    private final ClientService client;
    private final BookingService orders;

    public BookingHandler(TelegramService telegramService, UserSessionSaver saver,
                          ClientService client, BookingService orders) {
        this.telegramService = telegramService;
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
        Long chatId = userRequest.getChatId();
        if (client.getById(userRequest.getUpdate().getMessage().getFrom().getId()) == null) {
            log.info("Client was create");
            user = new Client();
            user.setId(userRequest.getUpdate().getMessage().getFrom().getId());
            user.setChatId(userRequest.getChatId());
            user.setName(userRequest.getUpdate().getMessage().getFrom().getUserName());
            client.add(user);
            userRequest.getUserSession().setState(ConversationState.WAIT_ORDER);
        } else {
            log.info("Client was update");
            user = client.getById(userRequest.getUpdate().getMessage().getFrom().getId());
        }
        if (userRequest.getUserSession().getState().equals(ConversationState.WAIT_ORDER)) {
            Booking order = new Booking();
            order.setClient(user);
            order.setPhone(0);
            order.setBushCount(0);
            orders.add(order);

            telegramService.sendMessage(chatId, "Выберите сорт⤵️", keyboard);

            UserSession userSession = userRequest.getUserSession();
            userSession.setState(ConversationState.WAITING_FOR_SORT);
            saver.saveSession(chatId, userSession);
            log.info("order was update and set state {}", userSession.getState());
        }else{
            telegramService.sendMessage(chatId,"Вы не закончили предыдущий заказ\n");
            switch (userRequest.getUserSession().getState()){
                case WAITING_FOR_COUNT -> telegramService.sendMessage(chatId,"Введите кол-во кг");
                case WAITING_FOR_PHONE -> telegramService.sendMessage(chatId,"Введите номер телефона");
                case WAITING_FOR_SORT -> telegramService.sendMessage(chatId,"Выберите сорт⤵️",keyboard);
            }
        }
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
