package com.anastluc.colorsoftheweb.data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;

/**
 *
 * @author lucas
 */
public class AlexaRetriever implements Retriever {

    // no public (free) rest api available but there is 
    // a zip file containing the top 1000000 sites
    // updated daily
    private static final String BASE_DIRECTORY = "c:\\cotw\\";
    private String archive_directory;

    public AlexaRetriever() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        archive_directory = BASE_DIRECTORY + year + "_" + month + "_" + dayOfMonth;
    }

    public void downloadAndStore() {
        try {

            File f = new File(archive_directory);
            if (!f.exists()) {
                f.mkdir();
            }

            File alexaZip = new File(archive_directory + "\\alexa_top_1m.zip");

            if (!alexaZip.exists() || alexaZip.length() < 1000000) {

                StopWatch sw = new StopWatch();
                sw.start();
                FileUtils.copyURLToFile(new URL("http://s3.amazonaws.com/alexa-static/top-1m.csv.zip"), alexaZip);
                sw.stop();
                System.out.println("download of alexa zip file took:" + sw.getTime() + " ms");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void extractZip() {
        //
        // find the zip file
        //

        File folder = new File(archive_directory);

        File zipFile = null;
        for (File f : folder.listFiles()) {
            if (f.toString().endsWith("zip")) {
                zipFile = f;
                break;
            }
        }

        StopWatch sw = new StopWatch();
        sw.start();
        unZipIt(zipFile.toString(), archive_directory);
        sw.stop();
        System.out.println("Unizipping took:" + sw.getTime() + " ms");
    }

    /**
     * Unzip it
     *
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder) {

        byte[] buffer = new byte[1024];

        try {

            //create output directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                System.out.println("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("unzipping done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<String> retrieve() {
        return retrieve(100);
    }
    
    public ArrayList<String> retrieve(int N) {
        //
        // find csv file
        //
        ArrayList<String> topSites = new ArrayList<String>();
        File folder = new File(archive_directory);
        File csvFile = null;

        for (File f : folder.listFiles()) {
            if (f.toString().endsWith("csv")) {
                csvFile = f;
                break;
            }
        }
        try {
            //
            // read the file and populate the list to return
            //
            FileInputStream is = new FileInputStream(csvFile);
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            int counter =0;
            while ((line = br.readLine()) != null) {
                String domain = line.split(",")[1];
                topSites.add(domain);
                counter++;
                if (counter==N){
                    break;
                }
            }
            br.close();
            in.close();
            is.close();            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return topSites;
    }

    public void retrieveAndWriteToFile(File f) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
