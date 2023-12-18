package com.urfu.tgbot.service;

import com.urfu.tgbot.model.User;
import com.urfu.tgbot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testAddUser_NewUser() {
        User newUser = new User("username", 1234567890);
        when(userRepository.findById(newUser.getChatID())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userService.addUser(newUser));
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testAddUser_ExistingUser() {
        User existingUser = new User("username", 1234567890);
        when(userRepository.findById(existingUser.getChatID())).thenReturn(Optional.of(existingUser));
        assertThrows(Exception.class, () -> userService.addUser(existingUser));
    }

    @Test
    void testDeleteUser_ExistingUser() {
        User existingUser = new User("username", 1234567890);
        when(userRepository.findById(existingUser.getChatID())).thenReturn(Optional.of(existingUser));

        assertDoesNotThrow(() -> userService.deleteUser(existingUser));
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void testDeleteUser_NonExistingUser() {
        User nonExistingUser = new User("username", 1234567890);
        when(userRepository.findById(nonExistingUser.getChatID())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.deleteUser(nonExistingUser));
    }

    @Test
    void testChangeUser() {
        User user = new User("username", 1234567890);
        User newUser = new User("newUsername", 1234567890);
        when(userRepository.findById(user.getChatID()))
                .thenReturn(Optional.of(user))  // первый вызов возвращает существующего пользователя
                .thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userService.changeUser(newUser));
        verify(userRepository).delete(user);
        verify(userRepository).save(newUser);
    }


    @Test
    void testChangeUser_NonExistingUser() {
        User nonExistingUser = new User("nonExistingUser", 1234567890);
        when(userRepository.findById(nonExistingUser.getChatID())).thenReturn(Optional.empty());
        verify(userRepository, never()).save(any(User.class));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testGetUserByChatID_ExistingUser() {
        User existingUser = new User("username", 1234567890);
        when(userRepository.findById(existingUser.getChatID())).thenReturn(Optional.of(existingUser));

        assertEquals(existingUser, userService.getUserByChatID(1234567890));
    }

    @Test
    void testGetUserByChatID_NonExistingUser() {
        long nonExistingChatID = 1234567890;
        when(userRepository.findById(nonExistingChatID)).thenReturn(Optional.empty());

        assertNull(userService.getUserByChatID(nonExistingChatID));
    }
}
