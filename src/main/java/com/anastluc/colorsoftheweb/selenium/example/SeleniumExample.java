/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anastluc.colorsoftheweb.selenium.example;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author lucas
 */
public class SeleniumExample {

    public static void main(String[] args) {

//        File file = new File("C:\\Selenium\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        WebDriver driver = new FirefoxDriver();
        driver.get("http://www.google.com");

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Now you can do whatever you need to do with it, for example copy somewhere
                    FileUtils.copyFile(scrFile, new File("c:\\Selenium\\screenshot.png"));
        } catch (IOException ex) {
            Logger.getLogger(SeleniumExample.class.getName()).log(Level.SEVERE, null, ex);
        }



//        WebElement element = driver.findElement(By.name("q"));
//        element.sendKeys("Cheese!");
//        element.submit();
//
//        System.out.println("Page title is:" + driver.getTitle());

//        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return d.getTitle().toLowerCase().startsWith("cheese!");
//            }
//        });

//        System.out.println("Page title is:" + driver.getTitle());

        driver.quit();

    }
}
