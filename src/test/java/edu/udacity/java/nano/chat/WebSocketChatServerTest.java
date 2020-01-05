package edu.udacity.java.nano.chat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebSocketConfig.class, Message.class})
@WebAppConfiguration
public class WebSocketChatServerTest {

    WebDriver webDriver;


    @Test
    public void loginAndJoin() throws Exception {
        webDriver = new HtmlUnitDriver();
        webDriver.get("http://localhost:8080");
        WebElement username = webDriver.findElement(By.name("username"));
        username.sendKeys("pradeep");

        WebElement login_form = webDriver.findElement(By.className("submit"));
        login_form.click();


        (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                WebElement username1 = webDriver.findElement(By.id("username"));
                System.out.println("username: " + username1.getText());
                return username1.getText().equals("pradeep");
            }
        });
    }


}