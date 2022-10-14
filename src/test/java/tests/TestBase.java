package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static helpers.Attach.*;

public class TestBase {

    @BeforeAll
    static void setup() {
        SelenideLogger.addListener("allureTest", new AllureSelenide());

        String browserName = System.getProperty("browser_name", "chrome");
        String browserVersion = System.getProperty("browser_version", "105.0");
        String browserSize = System.getProperty("browser_size", "1920x1080");
        String remoteUrl = System.getProperty("remote_url");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (System.getProperty("selenide.remote") != null) {
            Configuration.remote = System.getProperty("selenide.remote");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
        }

        Configuration.remote = remoteUrl;
        Configuration.browserCapabilities = capabilities;
        Configuration.browser = browserName;
        Configuration.browserVersion = browserVersion;
        Configuration.browserSize = browserSize;
        Configuration.browserPosition = "0x0";
        Configuration.baseUrl = "https://demowebshop.tricentis.com/";
        RestAssured.baseURI = "https://demowebshop.tricentis.com/";
    }

    @AfterEach
    void getAttachments() {
        takeScreenshot();
        addSource();
        addHTMLSource();
        addBrowserConsoleLog();
        addVideo();
    }
}