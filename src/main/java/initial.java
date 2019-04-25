import com.sun.glass.ui.View;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.util.concurrent.TimeUnit;


public class initial {

    public static WebDriver driver;
    public static WebDriverWait gWait;
    protected static String urlAddress = System.getProperty("urlAddress");
    protected static String globalWaitTime = System.getProperty("globalWaitTime");
    public static String user = System.getProperty("user");

    public static void invokeBrowser() {
        if (urlAddress == null || urlAddress.equals(".. pick element ...") || urlAddress.equals("$urlAddress")) {
            urlAddress = "saucedemo => https://www.saucedemo.com";
        }
        System.out.println(" Test started on url: " + urlAddress);
        if (globalWaitTime == null || globalWaitTime.equals(".. pick element ...") || globalWaitTime.equals("$globalWaitTime")) {
            globalWaitTime = "20";
        }
        if (user == null || user.equals(".. pick element ...") || user.equals("$user")) {
            user = "standard_user";
        }
        System.out.println("with globalWaitTime: " + globalWaitTime + "s");
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\seleniumautomation\\addins\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-start-maximized");
        options.addArguments("-incognito");
        driver = new ChromeDriver(options);
        String[] url= urlAddress.split(" ");
        driver.get(url[2]);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
        gWait = new WebDriverWait(driver, Integer.parseInt(globalWaitTime));
    }
}


