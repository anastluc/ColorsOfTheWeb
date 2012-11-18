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
    
    
}