package org.greenSnake.dto;

import lombok.Builder;
import lombok.Data;
import org.greenSnake.enums.ConversationState;

@Data
@Builder
public class UserSession {
    private Long chatId;
    private ConversationState state;
    private String city;
    private String text;
}
