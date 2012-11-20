/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anastluc.colorsoftheweb;

import com.anastluc.colorsoftheweb.data.AlexaRetriever;
import com.anastluc.colorsoftheweb.data.DatabaseManager;
import com.anastluc.colorsoftheweb.image.Histogram;
import com.anastluc.colorsoftheweb.selenium.Screenshoter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author lucas
 */
public class Invocator {

    public static void main(String[] args) {

//        String testFile = "c:\\Selenium\\screenshot.png";
//
//        Histogram h = new Histogram(testFile);
//
//
//        long[][] bins = h.calculateBins();
//        DatabaseManager dm = new DatabaseManager();
//                dm.test();
//        dm.storeImageFreqsToDb("no image storing at the moment - dull value", bins, h.getTotalPixels());
//
//        long[][][] rgbBin = h.calculateRGBBins();
//
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                for (int k = 0; k < 16; k++) {
//                    System.out.println("["+i+","+j+","+k+"]:"+rgbBin[i][j][k]);
//                }
//            }
//        }
//        
//        dm.storeImageRGBFreqsToDb("no image storing at the moment - dull value", rgbBin, h.getTotalPixels());
//
        
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        String BASE_DIRECTORY = "c:\\cotw\\";
        String archive_directory = BASE_DIRECTORY + year + "_" + month + "_" + dayOfMonth;
        
        Screenshoter ss = new Screenshoter();
        AlexaRetriever r = new AlexaRetriever();
        r.downloadAndStore();
        r.extractZip();
        ArrayList<String> sites = r.retrieve(250);
        DatabaseManager dm = new DatabaseManager();
        
        for (String site : sites){
            ss.take(site);
            String shotFile = archive_directory+"/"+site+".png";
            Histogram h = new Histogram(shotFile);
            long[][][] rgbBin = h.calculateRGBBins();
            dm.storeImageRGBFreqsToDb(shotFile, rgbBin, h.getTotalPixels());            
        }
        
        
        
        
    }
}
