package com.example.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TokenStore {

    private final ConcurrentMap<String, UserInfo> clients = new ConcurrentHashMap<>();

    public void save(final UserInfo client) {
        clients.put(client.getToken(), client);
    }

    public UserInfo find(final String token) {
        final UserInfo client = clients.get(token);
        if (client != null) {
            return client;
        }
        clients.remove(token);
        return null;
    }
}
