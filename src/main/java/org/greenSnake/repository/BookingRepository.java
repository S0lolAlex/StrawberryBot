package org.greenSnake.repository;

import org.greenSnake.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(nativeQuery = true, value = "select * from booking where client_id=:clientId and " +
            "id = (select max(id) from booking)")
    Booking getLast(Long clientId);
    @Query(nativeQuery = true,value = "select * from booking where client_id = :id")
    List<Booking> getAllByClientId(Long id);
}
