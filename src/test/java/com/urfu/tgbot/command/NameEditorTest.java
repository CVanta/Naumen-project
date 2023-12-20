//package com.urfu.tgbot.command;
//
//import com.urfu.tgbot.enumeration.StateEnum;
//import com.urfu.tgbot.model.User;
//import com.urfu.tgbot.service.StateService;
//import com.urfu.tgbot.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//public class NameEditorTest {
//    @Mock
//    private StateService stateService;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private NameEditor nameEditor;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testEditNameValidInputShouldReturnPhoneNumberPrompt() throws Exception {
//        long chatID = 123456L;
//        String name = "Петров Андрей Васильевич";
//        String username = "johndoe";
//        String expectedBotText = "Введите номер телефона";
//        String actualBotText = nameEditor.editName(chatID, name, username);
//        verify(userService).addUser(new User(name, chatID, username));
//        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_INPUT_PHONE_NUMBER);
//        assertEquals(expectedBotText, actualBotText);
//    }
//
//    @Test
//    public void testEditInstituteValidInputShouldReturnSuccessMessage() throws Exception {
//        long chatID = 123456L;
//        String institute = "Абоба";
//
//        User user = new User.Builder(chatID).username("Летов Игорь Фёдорович").tgUsername("taiga").institute(institute).build();
//        when(userService.getUserByChatID(chatID)).thenReturn(user);
//        String expectedBotText = "Вы успешно зарегистрировались. Ваш профиль: " +  "ФИО: " + "Летов Игорь Фёдорович" + "\n Номер телефона: " + "0" + "\n Институт: " + institute;
//        String actualBotText = nameEditor.editInstitute(chatID, institute);
//        verify(userService).addUser(user);
//        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_COMMAND);
//        assertEquals(expectedBotText, actualBotText);
//    }
//
//    @Test
//    public void testEditPhoneNumberValidInputShouldReturnInstitutePrompt() throws Exception {
//        long chatID = 123456L;
//        String phoneNumber = "71234567890";
//
//        User user = new User.Builder(chatID)
//                .username("Петров Андрей Васильевич")
//                .tgUsername("lll")
//                .phoneNumber(Long.parseLong(phoneNumber))
//                .build();
//        when(userService.getUserByChatID(chatID)).thenReturn(user);
//        String expectedBotText = "Введите ваш институт";
//        String actualBotText = nameEditor.editPhoneNumber(chatID, phoneNumber);
//        verify(stateService).updateState(chatID, StateEnum.WAITING_FOR_INPUT_INSTITUTE);
//        verify(userService).addUser(any(User.class));
//        assertEquals(expectedBotText, actualBotText);
//    }
//
//}