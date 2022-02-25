import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.testproject.sdk.drivers.ReportingDriver;
import io.testproject.sdk.drivers.TestProjectCapabilityType;
import io.testproject.sdk.drivers.android.AndroidDriver;
import io.testproject.sdk.interfaces.junit5.ExceptionsReporter;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.concurrent.TimeUnit;

public class AndroidTest implements ExceptionsReporter {

    private static AndroidDriver<? extends MobileElement> driver;


    @BeforeEach
    public void setup() throws Exception {
        driver = new AndroidDriver<>("<TP_DEV_TOKEN>", getCapabilities(), "Android Test");
    }

    @Override
    public ReportingDriver getDriver() {
        return driver;
    }

    @Test
    public void loginTest() throws InterruptedException {

        driver.manage().timeouts().implicitlyWait(15000, TimeUnit.MILLISECONDS);
        Thread.sleep(5000);

        AndroidElement scan_barcode = (AndroidElement) new WebDriverWait(driver, 30)
                .until(ExpectedConditions.elementToBeClickable(
                        MobileBy.xpath("//*[@text='SCAN BARCODE']")));
        scan_barcode.click();

        AndroidElement allow_btn = (AndroidElement) new WebDriverWait(driver, 30)
                .until(ExpectedConditions.elementToBeClickable(
                        MobileBy.xpath("//*[@text='While using the app']")));
        allow_btn.click();

        Thread.sleep(5000);

        //((JavascriptExecutor) driver).executeScript("browserstack_executor: {\"action\": \"cameraImageInjection\", \"arguments\": {\"imageUrl\": \"<QR_CODE_IMAGE_URL>\"}}");
        ((JavascriptExecutor) driver).executeScript("browserstack_executor: {\"action\": \"cameraImageInjection\", \"arguments\": {\"imageUrl\": \"<BAR_CODE_IMAGE_URL>\"}}");
        Thread.sleep(10000);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(TestProjectCapabilityType.CLOUD_URL, "http://hub.browserstack.com/wd/hub");
        capabilities.setCapability("os_version", "11.0");
        capabilities.setCapability("device", "Samsung Galaxy S21");
        capabilities.setCapability("browserstack.user", System.getenv("BROWSERSTACK_USERNAME"));
        capabilities.setCapability("browserstack.key", System.getenv("BROWSERSTACK_ACCESS_KEY"));
        capabilities.setCapability("app", "AndroidCameraInjection");
        capabilities.setCapability("browserstack.enableCameraImageInjection", "true");
        capabilities.setCapability("project", "CameraInjectionDemo");
        capabilities.setCapability("build", "DemoBuild1");
        capabilities.setCapability("name", "DemoTest1");
        return capabilities;
    }

}
