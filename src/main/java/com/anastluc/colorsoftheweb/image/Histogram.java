/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anastluc.colorsoftheweb.image;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.lang.time.StopWatch;

/**
 *
 * @author lucas
 */
public class Histogram {

    private String imageFilePath;
    private BufferedImage bi;
    
    public Histogram() {
    }
    
    public Histogram(String imageFilePath){
        this.imageFilePath = imageFilePath;
        
        try {
            bi = ImageIO.read(new File(imageFilePath));
        } catch (IOException ex) {
            System.err.println("Cannot read file"+ex.getMessage());
        }
    }

    public long[][] calculateBins() {
        
        StopWatch sw = new StopWatch();
        sw.start();
        int height = bi.getHeight();
        int width  = bi.getWidth();
        Raster raster = bi.getRaster();
        long [][]bins = new long[3][256];
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bins[0][raster.getSample(i,j,0)]++;
                bins[1][raster.getSample(i,j,1)]++;
                bins[2][raster.getSample(i,j,2)]++;                
            }            
        }
        
        sw.stop();
        System.out.println("Calculation took:"+sw.getTime()+" ms");
        
        return bins;
        
    }
    
    public long getTotalPixels(){
        return bi.getHeight()*bi.getWidth();
    }
    
    public long[][][] calculateRGBBins(){
        int height = bi.getHeight();
        int width  = bi.getWidth();
        Raster raster = bi.getRaster();
        long[][][] rgbBin = new long[16][16][16];//4096 possible colors
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int r_value = raster.getSample(i,j,0);
                int g_value = raster.getSample(i,j,1);
                int b_value = raster.getSample(i,j,2);               
                
                // x_value is between 0 and 255 (8bit channel)
                // this leads to 16777216 possible different colors                
                //reduce this to 4096 
                r_value = (int)Math.floor(r_value / 16);
                g_value = (int)Math.floor(g_value / 16);
                b_value = (int)Math.floor(b_value / 16);
                
                rgbBin[r_value][g_value][b_value]++;
            }            
        }        
        return rgbBin;
    }        
}