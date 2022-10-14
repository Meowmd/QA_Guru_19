package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.attachment;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class Attach {

    @Step("Сделать скрин")
    @Attachment(value = "Screenshot name", type = "image/png", fileExtension = "png")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Добавить исходник страницы (в тексте и без аннотации)")
    public static void addSource() {
        attachment("Source", webdriver().driver().source());
    }

    @Step("Добавить исходник страницы (как html страницу и с аннотацией)")
    @Attachment(value = "Source name", type = "text/html")
    public static byte[] addHTMLSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    @Step("Добавить логи консоли браузера")
    public static void addBrowserConsoleLog() {
        attachAsText(
                "Логи консоли браузера",
                String.join("\n", Selenide.getWebDriverLogs(BROWSER))
        );
    }

    @Step("Добавить видео")
    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + getVideoUrl()
                + "' type='video/mp4'></video></body></html>";
    }

    public static URL getVideoUrl() {
        String videoUrl = "https://selenoid.autotests.cloud/video/" + getSessionId() + ".mp4";

        try {
            return new URL(videoUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }
}