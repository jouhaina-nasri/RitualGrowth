package com.yoterra.hippo.services;

import com.yoterra.hippo.exceptions.MissingEntityException;
import com.yoterra.hippo.jpa.entities.Privilege;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.jpa.entities.users.UserStatus;
import com.yoterra.hippo.jpa.repositories.PrivilegeRepository;
import com.yoterra.hippo.jpa.repositories.UserRepository;
import com.yoterra.hippo.search.requests.UserSearchRequest;
import com.yoterra.hippo.search.requests.iar.InfoAssociationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.yoterra.hippo.jpa.entities.users.UserStatus.EXPIRED;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Tag("integration")
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UsersServiceImpl usersServiceImpl;

    @Autowired
    UserService userService;

    @Autowired
    PrivilegeRepository privilegeRepository;
    User user = new User();

    @BeforeEach
    void setUp() {
        Set<UserStatus> userStatuses = new HashSet<>();
        userStatuses.add(EXPIRED);
        Set<Privilege> privileges = new HashSet<>();
        user = new User("554789921089", "testing@example.com", "User Testing", null, userStatuses, privileges, "testtoken:testing@example.com", false);
        userRepository.save(user);
    }

    @Test
    void findAll() {
        assertFalse(userService.findAll().isEmpty());
    }

    @Test
    void findById(){
        assertEquals(userService.findById(user.getId()),user);
    }

    @Test
    void findByIdNotExisting(){
        assertNull(userService.findById(123L));
    }

    @Test
    void getUser()
    {
        InfoAssociationRequest iar = new InfoAssociationRequest();
        assertEquals(usersServiceImpl.getUser(user.getId(),iar),user);
    }

    @Test
    void getUserWithIdNotExisting()
    {
        Supplier<MissingEntityException> MISSING_USER_EXC_SUPPLIER =
                ()->new MissingEntityException("The user account does not exist or it has been deleted or deactivated.");
        InfoAssociationRequest iar = new InfoAssociationRequest();
        Exception exception = assertThrows(Exception.class, () -> usersServiceImpl.getUser(123L,iar));
        assertEquals(MISSING_USER_EXC_SUPPLIER.get().toString(),exception.toString());
    }

    @Test
    void findAllNotDuplicates(){
        List<User> users = userService.findAll();
        assertThat(users).doesNotHaveDuplicates();
    }
    @Test
    void loadUserByUsername() {
        assertEquals(usersServiceImpl.loadUserByUsername(user.getEmail()).getUsername(), user.getFullName());
    }

    @Test
    void searchUsers(){
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setFullNamePrefix(this.user.getFullName());
        assertTrue(usersServiceImpl.searchUsers(userSearchRequest).getContent().contains(user));
    }

    @Test
    void removePrivilege(){
        usersServiceImpl.removePrivilege(user.getId(),"");
        assertTrue(userService.findById(user.getId()).getPrivileges().isEmpty());
    }

    @AfterEach
    void teardown() {
        userRepository.delete(user);
    }

}