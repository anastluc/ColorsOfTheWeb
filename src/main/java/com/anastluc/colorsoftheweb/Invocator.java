/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anastluc.colorsoftheweb;

import com.anastluc.colorsoftheweb.data.DatabaseManager;
import com.anastluc.colorsoftheweb.image.Histogram;

/**
 *
 * @author lucas
 */
public class Invocator {
    public static void main(String[] args){
        
        String testFile = "c:\\Selenium\\screenshot.png";
        
        Histogram h = new Histogram(testFile);
        long[][] bins = h.calculateBins();
        DatabaseManager dm = new DatabaseManager();
//        dm.test();
        
        
        dm.storeImageFreqsToDb("no image storing at the moment - dull value", bins, h.getTotalPixels());
    }
}
