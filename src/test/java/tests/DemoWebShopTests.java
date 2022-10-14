package tests;

import tests.TestBase;
import tests.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

public class DemoWebShopTests extends TestBase {
    static UserData userData = new UserData();
    static UserData userDataChange = new UserData();
    static RegistrationPage registrationPage = new RegistrationPage();

    @Test
    @DisplayName("Регистрация нового пользователя")
    public void registrationTest() {
         registrationPage.registration(userData);
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void login() {
        registrationPage.login(userData);
        registrationPage.openUserAccount(userData);
    }

    @Test
    @DisplayName("Редактирование профиля UI")
    public void changeProfile() {
        registrationPage.registration(userData);
        registrationPage.login(userData);
        registrationPage.openUserAccount(userData);
        registrationPage.changeProfile(userDataChange);
    }
}