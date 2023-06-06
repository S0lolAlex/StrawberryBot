package org.greenSnake.services;

import lombok.RequiredArgsConstructor;
import org.greenSnake.entity.Client;
import org.greenSnake.repository.ClientRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public Client add(Client client){
        return repository.save(client);
    }

    public void delete(Client client){
        repository.delete(client);
    }

    public Client getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public void update(Client client){
        repository.save(client);
    }

    public Long getByPhone(Long phone){
        return repository.getIdByPhone(phone);
    }
}
