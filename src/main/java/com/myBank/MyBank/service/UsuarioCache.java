package com.myBank.MyBank.service;

import com.myBank.MyBank.entity.Users;

public class UsuarioCache {
    private static UsuarioCache instance;
    private Users users;

    private UsuarioCache() {
    }

    public static UsuarioCache getInstance() {
        if (instance == null) {
            instance = new UsuarioCache();
        }
        return instance;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
