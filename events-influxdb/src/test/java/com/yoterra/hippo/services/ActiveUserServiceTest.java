package com.yoterra.hippo.services;

import com.yoterra.hippo.jpa.entities.Privilege;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.entities.users.UserStatus;
import com.yoterra.hippo.jpa.repositories.ClientAppRepository;
import com.yoterra.hippo.jpa.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.yoterra.hippo.jpa.entities.users.UserStatus.EXPIRED;
import static org.junit.jupiter.api.Assertions.*;
@Tag("integration")
@SpringBootTest
class ActiveUserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ActiveUserService activeUserService;

    @Autowired
    private ClientAppRepository clientAppRepository;

    User user = new User();

    @BeforeEach
    void setUp() {
        Set<UserStatus> userStatuses = new HashSet<>();
        userStatuses.add(EXPIRED);
        Set<Privilege> privileges = new HashSet<>();
        user = new User("554789921089", "testing@yoterra.com", "User Testing", null, userStatuses, privileges, "testtoken:testing@yoterra.com", false);
        userRepository.save(user);
    }

    @Test
    void emailCheck() {
        assertTrue(activeUserService.emailCheck(user.getEmail()));
    }

    @Test
    void emailCheckNotExisting() {
        assertFalse(activeUserService.emailCheck("user@yoterra.com"));
    }


    @AfterEach
    void tearDown() {
        userRepository.delete(user);
    }

}