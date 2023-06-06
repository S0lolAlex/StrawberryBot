package org.greenSnake.services;

import lombok.Data;
import org.greenSnake.entity.Strawberry;
import org.greenSnake.repository.StrawberryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
public class StrawberryService {
    @Autowired
    private final StrawberryRepository repository;

    public Map<String,Float> getAll(){
        return repository.findAll().stream()
                .filter(Strawberry::isStatus)
                .collect(Collectors.toMap(Strawberry::getName,Strawberry::getPrice));
    }

    public Strawberry add(Strawberry strawberry){
        return repository.save(strawberry);
    }

    public void delete(Strawberry strawberry){
        repository.delete(strawberry);
    }

    public Strawberry getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public void update(Strawberry strawberry){
        repository.save(strawberry);
    }
}
