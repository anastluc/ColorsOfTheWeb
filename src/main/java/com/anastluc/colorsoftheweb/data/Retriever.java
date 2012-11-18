package com.anastluc.colorsoftheweb.data;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public interface Retriever {
    public ArrayList<String> retrieve();
    public void retrieveAndWriteToFile(File f);
}
