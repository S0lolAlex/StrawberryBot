package org.greenSnake.repository;

import org.greenSnake.entity.Strawberry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrawberryRepository extends JpaRepository<Strawberry,Long> {
}
