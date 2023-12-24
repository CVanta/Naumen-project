package com.urfu.tgbot.service;

import com.urfu.tgbot.model.User;
import com.urfu.tgbot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private final User user = new User("username", 1234567890);
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    /**
     * Проверяет, что метод addUser работает корректно.
     */
    @Test
    void testAddUser_NewUser() {
        assertDoesNotThrow(() -> userService.addUser(user));
        verify(userRepository, times(1)).save(user);
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