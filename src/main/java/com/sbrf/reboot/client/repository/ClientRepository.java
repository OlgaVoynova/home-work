package com.sbrf.reboot.client.repository;

import com.sbrf.reboot.client.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ClientRepository {
    private List<Client> clients;

    public ClientRepository() {
        this.clients = new ArrayList<>();
    }

    public void addCustomer (Client newClient) {
        clients.add(newClient);
    }

    public Stream<Client> getCustomersStream() {
        return clients.stream();
    }
}
