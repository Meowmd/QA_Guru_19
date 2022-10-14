package pages;

import com.codeborne.selenide.WebDriverRunner;
import tests.UserData;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;


public class RegistrationPage {
    private final String authCookieName = "NOPCOMMERCE.AUTH";
    private final String verificationTokenName = "__RequestVerificationToken";
    private final String verificationTokenInputValue = "MNp6ZQ-3YCxUU2D0Nu2QNiWgd7WQ1bKaNGY8gxxKghDsCf1UIG-FljYtEI4vGWPSBUv9y3nAllGk2g9nszCAvNwgGOnyDH9-JLzFHp3UhiY1";
    private final String verificationTokenHeaderValue = "KNx5vsWGQx8jdZsjTDqXT8_caoY-fhBoR3iiVKsaIaHd8SNQy-8SpTw2bGvmpa3JgvjGELEDPiUmI_f-UDmixNko8uDTcJaF51D47kOtB8c1";


    @DisplayName("Регистрация через API")
    public void registration(UserData userData) {
        given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(verificationTokenName, verificationTokenHeaderValue)
                .formParam("FirstName", userData.firstName)
                .formParam("LastName", userData.lastName)
                .formParam("Email", userData.email)
                .formParam("Password", userData.password)
                .formParam("ConfirmPassword", userData.password)
                .cookie(verificationTokenName, verificationTokenInputValue)
                .post("/register")
                .then()
//                 .statusCode(302)
                .log().status();

    }

    @DisplayName("Вход по API")
    public String login(UserData userData) {
        String authToken = given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("Email", userData.email)
                .formParam("Password", userData.password)
                .post("/login")
                .then()
                .log().status()
//                 .statusCode(302)
                .extract()
                .cookie(authCookieName);
        return authToken;
    }

    @DisplayName("Открытие UI страницы авторизациии")
    public void openUserAccount(UserData userData) {
        open("/Themes/DefaultClean/Content/images/logo.png");
        Cookie authCookie = new Cookie(authCookieName, login(userData));
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        open("");
        $(".account").shouldHave(text(userData.email));
    }

    @DisplayName("Редактирование профиля")
    public void changeProfile(UserData userDataAfterEdit) {
        open("/customer/info");
        $("#gender-male").click();
        $("#FirstName").setValue(userDataAfterEdit.firstName);
        $("#LastName").setValue(userDataAfterEdit.lastName);
        $("#Email").setValue(userDataAfterEdit.email);
        $("[value='Save']").click();
        $(".header-links .account");
        $("#FirstName").shouldHave(value(userDataAfterEdit.firstName));
        $("#LastName").shouldHave(value(userDataAfterEdit.lastName));
        $("#Email").shouldHave(value(userDataAfterEdit.email));
    }
}