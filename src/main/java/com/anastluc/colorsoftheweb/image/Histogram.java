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

/**
 *
 * @author lucas
 */
public class Histogram {

    public Histogram() {
    }

    public void calculate() {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File("c:\\Selenium\\screenshot.png"));
        } catch (IOException ex) {
            Logger.getLogger(Histogram.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int height = bi.getHeight();
        int width  = bi.getWidth();
        Raster raster = bi.getRaster();
        int [][]bins = new int[3][256];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bins[0][raster.getSample(i,j,0)]++;
                bins[1][raster.getSample(i,j,1)]++;
                bins[2][raster.getSample(i,j,2)]++;                
            }            
        }
        
        for (int i = 0; i < 256; i++) {
            System.out.print("("+bins[0][i]+","+bins[1][i]+","+bins[2][i]+") ");
            if (i%16==0){
                System.out.println("");
            }
        }
        
    }
}