import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.URL;

public class globalMethods extends initial {

    public String getErrorMessage(){
        String message = "";
        try {
            WebElement errorMessage = driver.findElement(By.xpath("//*[contains(@data-test,'error')]"));
            message = errorMessage.getAttribute("innerText");
        } catch (Exception e){
            e.printStackTrace();
        }

        return message;
    }

    public boolean verifyLink(String link) {
        boolean isFound = false;
        try {
            URL urlLink = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) urlLink.openConnection();
            connection.setConnectTimeout(4000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                isFound = true;
            }
            if (connection.getResponseCode() == 404) {
                System.out.println("Response code 404 for " + urlLink);
                isFound = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Broken link: " + link);
            isFound = false;
        }

        return isFound;
    }
}
