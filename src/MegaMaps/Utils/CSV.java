package MegaMaps.Utils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * A read only CSV file
 */
public class CSV implements AutoCloseable{
    private String filepath;
    private File file;
    private Scanner scanner;
    int num_fields = -1; //Number of fields, must be the same for each record

    /**
     *  Open a CSV file
     * @param filepath The location of the file. The filename.
     * @throws FileNotFoundException File is wrong format or not found
     */
    public CSV(String filepath) throws FileNotFoundException {
        this.filepath = filepath; //store filepath
        file = new File(filepath); //Create java file
        //Verify filepath type is csv
        JFileChooser chooser = new JFileChooser();
        if(!chooser.getTypeDescription(file).equals("Microsoft Excel Comma Separated Values File")){
            //throw new FileNotFoundException("Not csv file type: " + filepath);
        }
        //Create scanner and verify that file exists
        scanner = new Scanner(file);
    }

    /**
     * Read the next record(line) from the csv file
     * @return The fields from the records, or null if no more lines left
     * @throws Exception CSV does not have a valid format
     */
    public  String[] getNextRecord() throws Exception{
        if(!scanner.hasNextLine()){ //Check if next line exists
            return null;
        }
        String next_line = scanner.nextLine(); //Get the next line
        //todo add Quotes support
        //check for comment(#), not sure if supported
        /*
        if(next_line.charAt(0) == '#'){
            return getNextRecord();
        }*/
        //parse line
        String[] split_line = next_line.split(",");
        if(num_fields != -1 &&  split_line.length != num_fields){ //check if # of fields matches last record
            throw new Exception("CSV format error: Different number of fields in each record");
        }
        num_fields = split_line.length; //Set the # of fields for next record
        return split_line; //return data
    }


    /**
     * Close scanner
     * @throws Exception Something didn't work
     */
    @Override
    public void close() throws Exception {
        scanner.close();
    }
}
