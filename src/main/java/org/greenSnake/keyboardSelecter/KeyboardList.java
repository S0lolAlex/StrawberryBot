package org.greenSnake.keyboardSelecter;

import org.greenSnake.services.StrawberryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardList {
    @Autowired
    private StrawberryService service;
    public ReplyKeyboardMarkup buildMainMenu() {
        List<KeyboardButton> buttons = List.of(
                new KeyboardButton("Сделать заказ"),
                new KeyboardButton("Статус заказа"));
        KeyboardRow row1 = new KeyboardRow(buttons);

        KeyboardRow row2 = new KeyboardRow(List.of(new KeyboardButton("Что в наличии")));

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1, row2))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public InlineKeyboardMarkup buildSortMenu(){
        List<String> sorts = service.getAll().keySet().stream().toList();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for(String sort : sorts){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(sort);
            button.setCallbackData(sort);
            keyboard.add(List.of(button));
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }
}
