package org.greenSnake.repository;

import org.greenSnake.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    @Query(nativeQuery = true,value = "Select id from client where phone = :phone")
    public Long getIdByPhone(Long phone);
}
