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

    private final User user = new User("username", 1234567890);

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    /**
     * Проверяет, что метод addUser работает корректно.
     */
    @Test
    void testAddUser_NewUser() {
        when(userRepository.findById(user.getChatID())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userService.addUser(user));
        verify(userRepository, times(1)).save(user);
    }


    /**
     * Проверяет добавление существующего пользователя. Ожидает выбрасывания исключения, так как пользователь
     * с указанным chatID уже существует
     */
    @Test
    void testAddUser_ExistingUser() {
        when(userRepository.findById(user.getChatID())).thenReturn(Optional.of(user));
        Exception exception = assertThrows(Exception.class, () -> userService.addUser(user));
        assertEquals("user exists", exception.getMessage());
    }

    /**
     * Проверяет удаление существующего пользователя. Убеждается, что метод deleteUser корректно обрабатывает
     * существующего пользователя
     */
    @Test
    void testDeleteUser_ExistingUser() {
        when(userRepository.findById(user.getChatID())).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.deleteUser(user));
        verify(userRepository, times(1)).delete(user);
    }

    /**
     * Проверяет удаление несуществующего пользователя. Ожидает выбрасывания исключения, так как пользователя
     * с указанным chatID не существует
     */
    @Test
    void testDeleteUser_NonExistingUser() {
        when(userRepository.findById(user.getChatID())).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> userService.deleteUser(user));
        assertEquals("User not exist", exception.getMessage());
    }

    /**
     * Проверяет изменение существующего пользователя. Убеждается, что метод changeUser корректно обновляет
     * информацию о существующем пользователе
     */
    @Test
    void testChangeUser() {
        User newUser = new User("newUsername", 1234567890);
        when(userRepository.findById(user.getChatID()))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userService.changeUser(newUser));
        verify(userRepository).delete(user);
        verify(userRepository).save(newUser);
    }

    /**
     * Проверяет изменение информации о несуществующем пользователе. Убеждается, что метод changeUser не выполняет
     * никаких действий, так как пользователь с указанным chatID не существует
     */

    @Test
    void testChangeUser_NonExistingUser() {
        when(userRepository.findById(user.getChatID())).thenReturn(Optional.empty());
        verify(userRepository, never()).save(any(User.class));
        verify(userRepository, never()).delete(any(User.class));
    }

    /**
     * Проверяет получение существующего пользователя по chatID.
     * Убеждается, что метод getUserByChatID возвращает корректного пользователя
     */
    @Test
    void testGetUserByChatID_ExistingUser() {
        when(userRepository.findById(user.getChatID())).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUserByChatID(1234567890));
    }

    /**
     * Проверяет получение несуществующего пользователя по chatID.
     * Убеждается, что метод getUserByChatID возвращает null, так как пользователя с указанным chatID не существует
     */
    @Test
    void testGetUserByChatID_NonExistingUser() {
        long nonExistingChatID = 1234567890;
        when(userRepository.findById(nonExistingChatID)).thenReturn(Optional.empty());
        assertNull(userService.getUserByChatID(nonExistingChatID));
    }
}
