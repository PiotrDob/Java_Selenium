import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class saucedemo_test extends initial{

    initial initial;
    saucedemo saucedemo;

        @BeforeSuite
        public void setUpBeforeTestClass(){
            saucedemo = new saucedemo();
            initial = new initial();
            initial.invokeBrowser();
            saucedemo.initElements();
        }
        @AfterSuite
        public void closeWebDriver(){
            driver.close();
        }

        @Test(priority = 1)
        public void checkUserLoginList(){
            System.out.println("**** Starting test: checkUserLoginList ****");
            Assert.assertEquals(saucedemo.checkUserLoginList(),"All elements are visible.");
        }

        @Test(priority = 2)
        public void logUser(){
            System.out.println("**** Starting test: logUser ****");
            Assert.assertEquals(saucedemo.logUser(),"Successfully logged in.");
        }

        @Test(priority = 3)
        public void checkVisibilityOfImages(){
            System.out.println("**** Starting test: checkVisibilityOfImages ****");
            Assert.assertEquals(saucedemo.checkVisibilityOfImages(),"All images are visible.");
        }

        @Test(priority = 4)
        public void checkAddToCart(){
            System.out.println("**** Starting test: checkAddToCart ****");
            Assert.assertEquals(saucedemo.checkAddToCart(),"All visible products added successfully.");
        }

        @Test(priority = 5)
        public void checkRemoveFromCart(){
            System.out.println("**** Starting test: checkRemoveFromCart ****");
            Assert.assertEquals(saucedemo.checkRemoveFromCart(),"All visible products removed successfully.");
        }

        @Test(priority = 6)
        public void orderProducts(){
            System.out.println("**** Starting test: orderProducts ****");
            Assert.assertEquals(saucedemo.checkAddToCart(),"All visible products added successfully.");
            Assert.assertEquals(saucedemo.orderProducts(),"Order complete successfully.");
        }

}
