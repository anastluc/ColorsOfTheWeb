package com.anastluc.colorsoftheweb.selenium;

import com.anastluc.colorsoftheweb.selenium.example.SeleniumExample;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author lucas
 */
public class Screenshoter {

    private WebDriver driver;
    private static final String BASE_DIRECTORY = "c:\\cotw\\";
    private String archive_directory;

    public Screenshoter() {
        driver = new FirefoxDriver();
        
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        archive_directory = BASE_DIRECTORY + year + "_" + month + "_" + dayOfMonth;
    }

    public void take(String url) {
        driver.get("http://"+url);

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File outputFile = new File(archive_directory+"\\"+url+".png");
            System.out.println(outputFile.getAbsolutePath());
            FileUtils.copyFile(scrFile, outputFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //driver.quit();
    }
}
