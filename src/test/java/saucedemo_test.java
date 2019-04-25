import org.testng.Assert;
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

        @Test(priority = 1)
        public void checkUserLoginList(){
        System.out.println("**** Starting test: checkUserLoginList ****");
        Assert.assertEquals(saucedemo.checkUserLoginList(),"All elements are visible.");
        }

        @Test(priority = 2)
        public void login(){
            System.out.println("**** Starting test: logUser ****");
            Assert.assertEquals(saucedemo.logUser(),"Successfully logged in.");
        }




}
