package org.greenSnake;

import org.greenSnake.dto.UserRequest;
import org.greenSnake.handler.UserRequestHandler;
import org.greenSnake.handler.adminHandler.AdminRequestHandler;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class AdminDispatcher {
    private final List<AdminRequestHandler> handlers;

    /**
     * Pay attention at this constructor
     * Since we have some global handlers,
     * like command /start which can interrupt any conversation flow.
     * These global handlers should go first in the list
     */
    public AdminDispatcher(List<AdminRequestHandler> handlers) {
        this.handlers = handlers
                .stream()
                .sorted(Comparator
                        .comparing(AdminRequestHandler::isGlobal)
                        .reversed())
                .collect(Collectors.toList());
    }

    public boolean dispatch(UserRequest userRequest) {
        for (AdminRequestHandler adminRequestHandler : handlers) {
            if(adminRequestHandler.isApplicable(userRequest)){
                adminRequestHandler.handle(userRequest);
                return true;
            }
        }
        return false;
    }
}
