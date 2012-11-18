package com.anastluc.colorsoftheweb.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.apache.commons.lang.time.StopWatch;

/**
 *
 * @author lucas
 */
public class DatabaseManager {

    private MySQLConfig mySqlConfig;

    public DatabaseManager() {
        mySqlConfig = new MySQLConfig();
    }

    public boolean storeImageFreqsToDb(String imageFileName, long[][] bins, long pixels) {

        StopWatch sw = new StopWatch();
        sw.start();
        
        StringBuilder sql = new StringBuilder("INSERT INTO frequencies (id_image, r_value, g_value, b_value, frequency, frequency_perc) VALUES (?, ?, ?, ?, ?, ?)");

        PreparedStatement ps = null;
        try {
            ps = mySqlConfig.getConnection().prepareStatement(sql.toString());

            for (int i = 0; i < bins.length; i++) {
                for (int j = 0; j < 256; j++) {

                    ps.setInt(1, 1);
                    if (i==0){//setting R values
                        ps.setLong(2, j);
                        ps.setNull(3, java.sql.Types.NULL);
                        ps.setNull(4, java.sql.Types.NULL);
                    }else if (i==1){// G values
                        ps.setNull(2, java.sql.Types.NULL);
                        ps.setLong(3, j);
                        ps.setNull(4, java.sql.Types.NULL);
                    }else if (i==2){// B values
                        ps.setNull(2, java.sql.Types.NULL);
                        ps.setNull(3, java.sql.Types.NULL);
                        ps.setLong(4, j);
                    }
                    ps.setLong(5, bins[i][j]);

                    double freq_perc = ((double) bins[0][j]) / pixels;
                    ps.setDouble(6, freq_perc);
                    ps.addBatch();
                }
            }
            ps.executeBatch();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        sw.stop();
        System.out.println("Inserting to DB took:"+sw.getTime()+" ms");
        
        return true;
    }

    public void test() {
        PreparedStatement ps;
        try {
            ps = mySqlConfig.getConnection().prepareStatement("SELECT * FROM frequencies");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_image");
                System.out.println("id:" + id);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }


    }
}
