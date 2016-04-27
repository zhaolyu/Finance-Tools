import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Josue on 4/22/2016.
 */

public class CsvReaderWriter {
    private String fileName = null;
    private boolean parse = false;

    /**
     * @param fileName path of the file (String)
     * @param parse    to parse or not (boolean)
     */
    CsvReaderWriter(String fileName, boolean parse) {
        if (fileName != null) {
            if (fileName.contains(".csv")){
                this.fileName = fileName;
            }else {
                this.fileName = fileName + ".csv";
            }
        } else {
            throw new Error("File is null");
        }

        this.parse = parse;
    }

    /**
     * Reads every Line from file.
     *
     * @return Array List of every line(String).
     */
    public ArrayList<String> readLinesFromFile() {
        BufferedReader reader;
        try {
            // Reads the cvs file
            reader = new BufferedReader(new FileReader(this.fileName));
            String line;
            ArrayList<String> lines = new ArrayList<>();

            // Reads each line until it detect a null line
            while ((line = reader.readLine()) != null) {
                // parse the value to separate the values needed
                if (this.parse) {
                    String[] cols = line.split(",");
                    lines.add(parseValidDataOnly(cols));
                } else
                    lines.add(line);
            }

            reader.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param arrayColumns String array that was splited from the read line
     * @return the string line with the desired values removing Volume and Adj Close from the list
     */
    private String parseValidDataOnly(String[] arrayColumns) {
        String joinedString = "";
        int wordCount = 0;
        for (String word : arrayColumns) {
            if (wordCount != Lables.CLOSE.val()) {
                joinedString += word + ",";
            } else {
                joinedString += word;
                break;
            }
            wordCount++;
        }
        return joinedString;
    }

}
