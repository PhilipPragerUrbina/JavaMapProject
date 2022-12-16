package MegaMaps.Testing.Datasets;

import MegaMaps.Utils.CSV;
import MegaMaps.Utils.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Open a two column csv file and load into data set(String data)
 */
public class FileDataSet implements Dataset<String,String> {
    ArrayList<Pair<String,String>> data= new ArrayList<>();

    /**
     * Read the first two columns of the csv  file
     * @param filename The file location
     *  Will not keep reading if less than two columns
     */
    public FileDataSet(String filename){
        try {
            CSV file = new CSV(filename);
            while (true){ //read data
                String[] entry = file.getNextRecord();
                if(entry == null || entry.length < 2){ //EOF or end of data
                    return;
                }
                data.add(new Pair<>(entry[0],entry[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Pair<String, String> getEntry(int index) {
        return data.get(index);
    }
}
