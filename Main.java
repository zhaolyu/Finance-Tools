import java.io.*;
import java.util.ArrayList;

public class Main {
    // Change it as your path indicates
    static String companyName = "BAC";
    static boolean consoleOutput = false;
    static TextString textFileString = new TextString();
    static TextString csvFileString = new TextString();

    public static void main(String[] args) {
        MyConsoleMenu menu = new MyConsoleMenu();
        String originalCSV = menu.getFileName();

        CsvReaderWriter csvFile = new CsvReaderWriter(originalCSV, true);
        ArrayList<String> lines = csvFile.readLinesFromFile();

        if (lines != null) {
            // Sort from oldest to newest
            ArrayList<String> sortedCSV = sortFromOldestToNewest(lines);
            // Detect engulfing
            MyEngulfing engulfing = new MyEngulfing(sortedCSV);

            ArrayList<Integer> bullishList = engulfing.getBullishIndexList();
            ArrayList<Integer> bearish = engulfing.getBearishIndexList();

            System.out.println("Bull engulfing found: " + engulfing.getBullishCount());
            System.out.println("Bear engulfing found: " + engulfing.getBearishCount());
            if (bullishList != null) {
                engulfing.loopEngulfing(sortedCSV, bullishList, true);
            }
        }
    }

    static void writeFile(String content) {
        File file = new File("output.csv");

        // if file doesnt exists, then create it
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param list or array that will be sorted
     * @return the sorted list from oldest to newest
     */
    private static ArrayList<String> sortFromOldestToNewest(ArrayList<String> list) {
        ArrayList<String> newList = new ArrayList<>();
        newList.add(list.get(0));
        for (int end = list.size() - 1; end > 0; end--)
            newList.add(list.get(end));
        return newList;
    }
}