import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class saucedemo extends initial {

    globalMethods globalMethods;

    @FindBy(xpath= "//input[@id=\"user-name\"]")
    WebElement homePageUserName;
    @FindBy(xpath= "//input[@id=\"password\"]")
    WebElement homePageUserPassword;
    @FindBy(xpath= "//input[(contains(@class,'btn_action'))and(@value='LOGIN')]")
    WebElement homePageLoginButton;
    @FindBy(xpath= "//a[contains(@class, 'shopping_cart_link')]")
    WebElement cartButton;
    @FindBy(xpath= "//a[(contains(@class,'checkout_button'))and(text()='CHECKOUT')]")
    WebElement checkoutCartButton;
    @FindBy(xpath= "//input[@id='first-name']")
    WebElement checkoutFirstName;
    @FindBy(xpath= "//input[@id='last-name']")
    WebElement checkoutLastName;
    @FindBy(xpath= "//input[@id='postal-code']")
    WebElement checkoutPostalCode;
    @FindBy(xpath= "//input[(contains(@class,'cart_button'))and(@value='CONTINUE')]")
    WebElement checkoutContinueButton;
    @FindBy(xpath= "//a[(contains(@class,'cart_button'))and(text()='FINISH')]")
    WebElement checkoutFinishButton;

    public void initElements(){
        globalMethods = new globalMethods();
        PageFactory.initElements(driver, this);
    }

    public String checkTotalPayment() {
        double itemsPrice = 0;
        List<WebElement> prices = driver.findElements(By.xpath("//div[contains(@class,'inventory_item_price')]"));
        for(WebElement price:prices) {
           // itemsPrice = (double)Math.round(value * 100000d) / 100000d .replace(".",",")
            double itemPrice = Double.parseDouble(price.getAttribute("innerText").substring(1));
            itemsPrice += itemPrice;
        }

        return ("$" + Double.toString(itemsPrice));
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
            e.printStackTrace();
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
            e.printStackTrace();
            result = "Login failed.";
            System.out.println("Error message: " + globalMethods.getErrorMessage());
            errorFound = true;
        }
        if (!errorFound) {
            result = "Successfully logged in.";
        }

        System.out.println("**** Result: " + result + " ****");
        return result;
    }

    public String checkVisibilityOfImages(){
        String result = "";
        boolean errorFound = false;
        boolean isFound;
        int failedCount = 0;
        try {
            List<WebElement> links = driver.findElements(By.tagName("img"));
            for(int i=0; i<links.size(); i++) {
                WebElement element = links.get(i);
                String link=element.getAttribute("src");
                isFound = globalMethods.verifyLink(link);
                if (!isFound) {
                    failedCount +=1;
                    errorFound = true;
                }
            }
            System.out.println("Found " + (links.size() - failedCount) + " of " + links.size() + " visible images.");
            if (errorFound){
                System.out.println("Found " + failedCount  + " of " + links.size() + " broken images.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = "Errors founded.";
            errorFound = true;
        }
        if (!errorFound) {
            result = "All images are visible.";
        }

        System.out.println("**** Result: " + result + " ****");
        return result;
    }

    public String checkAddToCart(){
        String result = "";
        boolean errorFound = false;
        try {
            List<WebElement> productsToAdd = driver.findElements(By.xpath("//button[text()='ADD TO CART']"));
            if (productsToAdd.size() > 0){
                for(WebElement product:productsToAdd) {
                    product.click();
                }
                productsToAdd = driver.findElements(By.xpath("//button[text()='ADD TO CART']"));
                Integer cartCounter = Integer.parseInt(driver.findElement(By.xpath("//span[contains(@class,'shopping_cart_badge')]")).getAttribute("innerText"));
                if (productsToAdd.size() == 0 && cartCounter == 6){
                    System.out.println("All visible products added successfully.");
                } else {
                    System.out.println("Not all visible products added successfully.");
                    result = "Not all visible products added successfully.";
                    errorFound = true;
                }
                if (errorFound){
                    List<WebElement> notAddedProducts = driver.findElements(By.xpath("//button[text()='ADD TO CART']" +
                            "/ancestor::div[contains(@class,'inventory_item')]//div[contains(@class,'inventory_item_name')]"));
                    for(WebElement product:notAddedProducts){
                        System.out.println("'" + product.getAttribute("innerText") + "' not added successfully.");
                    }
                }
            } else {
                System.out.println("No visible products to add.");
                result = ("No visible products to add.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = "Errors founded.";
            errorFound = true;
        }
        if (!errorFound) {
            result = "All visible products added successfully.";
        }

        System.out.println("**** Result: " + result + " ****");
        return result;
    }

    public String checkRemoveFromCart(){
        String result = "";
        boolean errorFound = false;
        try {
            List<WebElement> productsToRemove = driver.findElements(By.xpath("//button[text()='REMOVE']"));
            if (productsToRemove.size() > 0){
                for(WebElement product:productsToRemove) {
                    product.click();
                }
                productsToRemove = driver.findElements(By.xpath("//button[text()='REMOVE']"));
                List<WebElement>  allRemoved = driver.findElements(By.xpath("//span[contains(@class,'shopping_cart_badge')]"));
                if (productsToRemove.size() == 0 && allRemoved.size() == 0){
                    System.out.println("All visible products removed successfully.");
                } else {
                    System.out.println("Not all visible products removed successfully.");
                    result = "Not all visible products removed successfully.";
                    errorFound = true;
                }
                if (errorFound){
                    List<WebElement> notAddedProducts = driver.findElements(By.xpath("//button[text()='REMOVE']" +
                            "/ancestor::div[contains(@class,'inventory_item')]//div[contains(@class,'inventory_item_name')]"));
                    for(WebElement product:notAddedProducts){
                        System.out.println("'" + product.getAttribute("innerText") + "' not removed successfully.");
                    }
                }
            } else {
                System.out.println("No visible products to remove.");
                result = ("No visible products to remove.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = "Errors founded.";
            errorFound = true;
        }
        if (!errorFound) {
            result = "All visible products removed successfully.";
        }

        System.out.println("**** Result: " + result + " ****");
        return result;
    }

    public String orderProducts(){
        String result = "";
        boolean errorFound = false;
        try {
            cartButton.click();
            gWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[(contains(@class,'subheader'))and(text()='Your Cart')]")));
            js.executeScript("arguments[0].scrollIntoView();",checkoutCartButton);
            checkoutCartButton.click();
            gWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[(contains(@class,'subheader'))and(text()='Checkout: Your Information')]")));
            checkoutFirstName.sendKeys("Piotr");
            checkoutLastName.sendKeys("Dob.");
            checkoutPostalCode.sendKeys("11-111");
            Assert.assertEquals(checkoutFirstName.getAttribute("value"),"Piotr");
            Assert.assertEquals(checkoutLastName.getAttribute("value"),"Dob.");
            Assert.assertEquals(checkoutPostalCode.getAttribute("value"),"11-111");
            checkoutContinueButton.click();
            String sumOfPrices =  checkTotalPayment();
            String sumOfTotal = driver.findElement(By.xpath("//div[contains(@class,'summary_subtotal_label')]")).getAttribute("innerText").substring(12);
            Assert.assertEquals(sumOfPrices,sumOfTotal);
            gWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[(contains(@class,'subheader'))and(text()='Checkout: Overview')]")));
            js.executeScript("arguments[0].scrollIntoView();",checkoutFinishButton);
            checkoutFinishButton.click();
            gWait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[contains(@class,'complete-header')]"),"THANK YOU FOR YOUR ORDER"));
            List<WebElement>  allRemoved = driver.findElements(By.xpath("//span[contains(@class,'shopping_cart_badge')]"));
            if (allRemoved.size() != 0) {
                System.out.println("Not all products removed from cart.");
                result = "Not all products removed from cart.";
                errorFound = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = "Order products failed.";
            System.out.println("Order products failed.");
            errorFound = true;
        }
        if (!errorFound) {
            result = "Order complete successfully.";
        }

        System.out.println("**** Result: " + result + " ****");
        return result;
    }
}
