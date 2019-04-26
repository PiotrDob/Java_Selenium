import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class saucedemo extends initial {


    @FindBy(xpath= "//input[@id=\"user-name\"]")
    WebElement homePageUserName;
    @FindBy(xpath= "//input[@id=\"password\"]")
    WebElement homePageUserPassword;
    @FindBy(xpath= "//input[(contains(@class,'btn_action'))and(@value='LOGIN')]")
    WebElement homePageLoginButton;


    public String getErrorMessage(){
        String message = "";
        try {
            WebElement errorMessage = driver.findElement(By.xpath("//*[contains(@data-test,'error')]"));
            message = errorMessage.getAttribute("innerText");
        } catch (Exception e){
            System.out.println(e.toString());
        }
        
        return message;
    }



    public void initElements(){
    PageFactory.initElements(driver, this);
    }

    public String checkUserLoginList(){
        String result = "";
        boolean errorFound = false;
        try {
            String[] logins = {"standard_user", "locked_out_user", "problem_user", "performance_glitch_user"};
            for(String login:logins){
                gWait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[contains(@class,'login_credentials')]"), login));
            }
        }catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(e.toString());
            result = "Not all elements are found.";
            errorFound = true;
        }
        if(!errorFound){
            result = "All elements are visible.";
        }

        System.out.println("**** Result: " + result + " ****");
        return result;
    }
    public String logUser(){
        String result = "";
        boolean errorFound = false;
        homePageUserName.sendKeys(user);
        homePageUserPassword.sendKeys("secret_sauce");
        homePageLoginButton.click();
        try {
            gWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[(contains(@class,'product_label'))and(text()='Products')]")));
        }catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(e.toString());
            result = "Login failed.";
            System.out.println("Error message: " + getErrorMessage());
            errorFound = true;
        }
        if (!errorFound) {
            result = "Successfully logged in.";
        }

        System.out.println("**** Result: " + result + " ****");
        return result;
    }
}
