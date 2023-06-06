package com.joris;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;


public class Tests {
    WebDriver driver = new ChromeDriver();

    @Before
    public void setup() {
        driver.get("https://the-internet.herokuapp.com");
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void ABTest() {
        WebElement ABLink = driver.findElement(By.linkText("A/B Testing"));
        ABLink.click();

        WebElement title = driver.findElement(By.cssSelector("h3"));
        assertEquals("A/B Test Variation 1", title.getText());
    }

    @Test
    public void AddRemoveTest() {
        WebElement addRemove = driver.findElement(By.linkText("Add/Remove Elements"));
        addRemove.click();
        WebElement addButton = driver.findElement(By.cssSelector("button[onclick*='addElement()']"));
        addButton.click();
        WebElement deleteButton = driver.findElement(By.cssSelector("button[onclick*='deleteElement()']"));
        deleteButton.click();
        try {
            WebElement deleteButtonNotFound = driver.findElement(By.cssSelector("button[onclick*='deleteElement()']"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            assertTrue(true);
        }
    }

    @Test
    public void BasicAuthentication() {
        WebElement BasicAuthenticationLink = driver.findElement(By.linkText("Basic Auth"));
        BasicAuthenticationLink.click();

        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

        WebElement title = driver.findElement(By.cssSelector("h3"));
        assertEquals("Basic Auth", title.getText());
    }

    @Test
    public void KeyPressesTest() {
        WebElement keypressesLink = driver.findElement(By.linkText("Key Presses"));
        keypressesLink.click();

        WebElement textBox = driver.findElement(By.id("target"));
        textBox.sendKeys(Keys.BACK_SPACE);

        WebElement pressedKey = driver.findElement(By.id("result"));
        assertEquals("You entered: BACK_SPACE", pressedKey.getText());
    }
    @Test
    public void checkboxTest() {
        WebElement checkboxesLink = driver.findElement(By.linkText("Checkboxes"));
        checkboxesLink.click();

        WebElement checkBox = driver.findElement(By.cssSelector("input"));
        checkBox.click();

        assertEquals("true",checkBox.getAttribute("checked"));

    }

    @Test
    public void dropdownTest() {
        WebElement dropdownLink = driver.findElement(By.linkText("Dropdown"));
        dropdownLink.click();
        String option = "Option 1";
        Select dropdown = new Select(driver.findElement(By.id("dropdown")));

        dropdown.selectByVisibleText(option);

        assertEquals(dropdown.getFirstSelectedOption().getText(),option);

    }
    @Test
    public void hoverTest() {
        WebElement hoverLink = driver.findElement(By.linkText("Hovers"));
        hoverLink.click();

        WebElement firstImage = driver.findElement(By.className("figure"));
        Actions action = new Actions(driver);
        action.moveToElement(firstImage).perform();

        WebElement subTitle = driver.findElement(By.cssSelector("h5"));
        action.moveToElement(subTitle);
        assertEquals("name: user1", subTitle.getText());

    }
    @Test
    public void sliderTest() {
        WebElement horizontalSliderLink = driver.findElement(By.linkText("Horizontal Slider"));
        horizontalSliderLink.click();

        WebElement slider = driver.findElement(By.cssSelector("input"));
        Dimension sliderSize = slider.getSize();
        Point sliderLocation = slider.getLocation();

        int slideTo = sliderSize.getWidth() / 2;

        int targetPosition = sliderLocation.getX() + slideTo;

        Actions actions = new Actions(driver);
        actions.dragAndDropBy(slider, targetPosition, 0).perform();

        String sliderValue = driver.findElement(By.id("range")).getText();
        assertEquals("value is not correct", "5", sliderValue);
    }
    @Test
    public void DragAndDrop() {
        WebElement dragAndDropLink = driver.findElement(By.linkText("Drag and Drop"));
        dragAndDropLink.click();

        WebElement From=driver.findElement(By.cssSelector("header:contains(A)"));
        WebElement To=driver.findElement(By.cssSelector("header:contains('B')"));

        Actions action=new Actions(driver);
        action.dragAndDrop(From, To).build().perform();


    }

    @Test
    public void loginTest() {
        WebElement formAuthentication = driver.findElement(By.linkText("Form Authentication"));
        formAuthentication.click();
        String username = "tomsmith";
        String password = "SuperSecretPassword!";

        WebElement textBoxUsername = driver.findElement(By.id("username"));
        WebElement textBoxPassword = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.className("fa-sign-in"));

        textBoxUsername.sendKeys(username);
        textBoxPassword.sendKeys(password);
        loginButton.click();

        WebElement greenBannerLoginSuccess = driver.findElement(By.id("flash"));
        String loginSuccessText = greenBannerLoginSuccess.getText();
        loginSuccessText = loginSuccessText.substring(0, loginSuccessText.length() - 2);
        assertEquals("You logged into a secure area!", loginSuccessText);

        WebElement logoutButton = driver.findElement(By.className("icon-signout"));
        logoutButton.click();
    }

}
