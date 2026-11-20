package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.users.User;

import java.util.List;

public interface UserService {

    public List<User> findAll();

    public User findById(Long id);

}
