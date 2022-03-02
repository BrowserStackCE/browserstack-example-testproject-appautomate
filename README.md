# testproject-appium-app-browserstack
This repository demonstrates how to run Appium tests along with Camera Injection for QR/Bar Code scanning using [TestProjectSDK](https://app.testproject.io/#/integrations/sdk) on BrowserStack App Automate.

<div align="center">
<img src = "https://www.browserstack.com/images/layout/browserstack-logo-600x315.png" > <br>
<img src = "https://webdriver.io/img/webdriverio.png"  height="140px">
</div>

Code samples to get started with Appium tests for your Native App using TestProject.

## Setup

### Prerequisites

* TestProject account
* BrowserStack account 
* You must have Java Development Kit (JDK) 11 or newer installed. If you're using IDE like intelliJ IDe/Eclipse please make sure the Project is using Java 11 or above.

### Install the dependencies

For a Maven project, add the following to your pom.xml file:

```
<dependency>
  <groupId>io.testproject</groupId>
  <artifactId>java-sdk</artifactId>
  <version>1.2.3-RELEASE</version>
</dependency>
```

Or,

For a Gradle project, add the following to your build.gradle file:

```
compileOnly 'io.testproject:java-sdk:1.2.4-RELEASE'
```

## Test Development

Using a TestProject driver is exactly identical to using a Android driver. Changing the import statement is enough in most cases.

Here's an example of how to create a TestProject version of ChromeDriver:

```
....
import io.testproject.sdk.drivers.ReportingDriver;
import io.testproject.sdk.drivers.TestProjectCapabilityType;
import io.testproject.sdk.drivers.android.AndroidDriver;
import io.testproject.sdk.interfaces.junit5.ExceptionsReporter;
....

public class AndroidTest implements ExceptionsReporter {
    private static AndroidDriver<? extends MobileElement> driver;

    @BeforeEach
    public void setup() throws Exception {
        driver = new AndroidDriver<>("<TP_DEV_TOKEN>", getCapabilities(), "Android Test");
    }
    .....
}  
```

Make sure to [configure a development token](https://app.testproject.io/#/integrations/sdk) before running this example.

Here a complete test example for a Android Test with Camera Injection:

```

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

```


### Run first test:
  - Clone the repo
  - Import the Maven Project in your IDE (optional)
  - If you're running from IDE, just right-click and run AndroidSuite.xml file
  - If you're running from Terminal, navigate to the cloned repo directory and run command : ``` mvn clean test -Dsurefire.suiteXmlFiles=src/test/java/AndroidSuite.xml ```
  - Follow the steps outlined in the documentation - [Get Started with your first test on App Automate](https://www.browserstack.com/docs/app-automate/appium/getting-started/java/junit)

### Speed up test execution with parallel testing :

- Follow the steps outlined in the documentation - [Get Started with parallel testing on App Automate](https://www.browserstack.com/docs/app-automate/appium/getting-started/java/junit/parallelize-tests)

### Use Local testing for apps that access resources hosted in development or testing environments :

- Follow the steps outlined in the documentation - [Get Started with Local testing on App Automate](https://www.browserstack.com/docs/app-automate/appium/getting-started/java/junit/local-testing)

**Note**: For other test frameworks supported by App-Automate refer our [Developer documentation](https://www.browserstack.com/docs/)

## Getting Help

If you are running into any issues or have any queries, please check [Browserstack Support page](https://www.browserstack.com/support/app-automate) or [get in touch with us](https://www.browserstack.com/contact?ref=help).

