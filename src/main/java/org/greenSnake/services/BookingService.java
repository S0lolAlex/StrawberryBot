package org.greenSnake.services;

import lombok.RequiredArgsConstructor;
import org.greenSnake.entity.Booking;
import org.greenSnake.repository.BookingRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository repository;

    public Booking add(Booking order) {
        return repository.save(order);
    }

    public List<Booking> getAll() {
        return repository.findAll();
    }

    public Booking getLastOrder(Long id) {
        return repository.getLast(id);
    }

    public List<Booking> getAllById(Long id){
        return repository.getAllByClientId(id);
    }

    public boolean isEmpty(Long id) {
        Booking order = repository.getLast(id);
        boolean sort = order.getSort() == null;
        boolean phone = order.getPhone() == 0;
        boolean bush = order.getBushCount() == 0;
        return sort && phone && bush;
    }

    public void delete(Booking order) {
        repository.delete(order);
    }

    public Booking getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void update(Booking order) {
        add(order);
    }
}
