import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {
    // Change it as your path indicates
    static String originalCVS = "table.csv";
    static String companyName = "BAC";
    static String fullTextContent = "";
    private static int BullishEngulfing = 0;
    private static int BearishEngulfing = 0;

    public static void main(String[] args) {
        writeFile("jodete");
        ArrayList<String> lines = readLinesFromFile();

        if (lines != null) {
            // Sort from oldest to newest
            ArrayList<String> sortedCVS = sortFromOldestToNewest(lines);
            // Detect engulfing
            ArrayList<ArrayList<Integer>> engulfing = detectEngulfing(sortedCVS);

            ArrayList<Integer> bullish = engulfing.get(Lables.BULLISH.val());
            ArrayList<Integer> bearish = engulfing.get(Lables.BEARISH.val());
            loopEngulfing(sortedCVS, bullish, true);
        }
    }

    private static void loopEngulfing(ArrayList<String> sortedCVS, ArrayList<Integer> engulfingType, boolean isBullish) {
        for (int index : engulfingType) {
//                System.out.println("index: " + index);
            ArrayList<String> listOfTenDays = grabNextTenValues(sortedCVS, index);
            ArrayList<String> percentageOfList = getProfitPercentage(listOfTenDays, isBullish);

            String tableTitle;
            if (isBullish) {
                tableTitle = "\nBULL: " + companyName;
                System.out.print(tableTitle);
            } else {
                tableTitle = "\nBEAR: " + companyName;
                System.out.print(tableTitle);
            }
            printTable(listOfTenDays, percentageOfList);
        }
    }

    private static void printTable(ArrayList<String> rowList, ArrayList<String> percentageOfList) {
        ArrayList<ArrayList<String>> datesNClosings = getDateAndCLosing(rowList);
        ArrayList<String> dates = datesNClosings.get(0);
        ArrayList<String> closingProfits = datesNClosings.get(1);

        String charLimits = "| %-15s";
        String titleDate = " date of engulfing ---> " + dates.get(0) + "\n";

        System.out.println(titleDate);
        printDelimeters();
        System.out.format(charLimits, "**************");
        printDays();
        printDelimeters();
        System.out.format(charLimits, "Date");
        printRowCategory(dates);
        System.out.format(charLimits, "Price");
        printRowCategory(closingProfits);
        System.out.format(charLimits, "Profit(%)");
        printRowCategory(percentageOfList);
        printDelimeters();
    }

//    private static String formatString(String originalString) {
//        String formattedString = originalString;
//        int charLimit = 16;
//        int spacesLeft = charLimit - originalString.length();
//        for (int spaceCount = 0; spaceCount < spacesLeft; spaceCount++) {
//            formattedString += " ";
//        }
//        return formattedString;
//    }

    private static void printDays() {
        String charLimits = "| %-15s";
        for (int day = 0; day < 11; day++) {
            System.out.format(charLimits, "Day " + day);
        }
        System.out.print("|\n");
    }

    private static void printDelimeters() {
        for (int day = 0; day < 12; day++) {
            System.out.print("+----------------");
        }
        System.out.print("+\n");
    }


    private static void printRowCategory(ArrayList<String> category) {
        String charLimits = "| %-15s";
        for (String aCategory : category) {
            System.out.format(charLimits, aCategory);
        }
        System.out.print("|\n");
    }

    private static ArrayList<ArrayList<String>> getDateAndCLosing(ArrayList<String> rowList) {
        ArrayList<ArrayList<String>> datesNclosing = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> closingProfits = new ArrayList<>();

        for (String row : rowList) {
            String[] rowData = splitRow(row);
            dates.add(rowData[Lables.DATE.val()]);
            closingProfits.add(rowData[Lables.CLOSE.val()]);
        }

        datesNclosing.add(dates);
        datesNclosing.add(closingProfits);

        return datesNclosing;
    }

    private static ArrayList<String> getProfitPercentage(ArrayList<String> listOfTenDates, boolean isBullish) {
        ArrayList<String> percentageList = new ArrayList<>();
        percentageList.add("N/A");
        percentageList.add("0.00");
        // we skip index 0 because that's our engulfing
        String[] day1 = splitRow(listOfTenDates.get(1));
        for (int i = 2; i < listOfTenDates.size(); i++) {
            String[] currentDay = splitRow(listOfTenDates.get(i));
            percentageList.add(doProfitOperation(day1[Lables.CLOSE.val()], currentDay[Lables.CLOSE.val()], isBullish));
//            System.out.println(doProfitOperation(day1[Lables.CLOSE.val()], currentDay[Lables.CLOSE.val()], isBullish));
        }
        return percentageList;
    }

    private static String[] splitRow(String row) {
        return row.split(",");
    }

    private static String doProfitOperation(String firstDayAfterEngulfing, String currentDay, boolean isBullish) {
        double subtraction;
        if (isBullish) {
            subtraction = (Double.parseDouble(currentDay) - Double.parseDouble(firstDayAfterEngulfing));
        } else {
            subtraction = (Double.parseDouble(firstDayAfterEngulfing) - Double.parseDouble(currentDay));
        }

        double percentage = (subtraction / Double.parseDouble(firstDayAfterEngulfing)) * 100;
        DecimalFormat dec = new DecimalFormat("0.00");
        return String.valueOf(dec.format(percentage));
    }

    private static ArrayList<String> grabNextTenValues(ArrayList<String> list, int startIndex) {
        ArrayList<String> newList = new ArrayList<>();
        // index 0 will be our engulfing the next 10 will be our data needed
        for (int loops = 0; loops < 11; loops++) {
            newList.add(list.get(startIndex));
            startIndex++;
        }
        return newList;
    }

    private static ArrayList<String> readLinesFromFile() {
        BufferedReader reader;
        try {
            // Reads the cvs file
            reader = new BufferedReader(new FileReader(originalCVS));
            String line;
            ArrayList<String> lines = new ArrayList<>();

            // Reads each line until it detect a null line
            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(",");
                // parse the value to separate the values needed
                lines.add(parseValidDataOnly(cols));
            }
            reader.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeFile(String content) {
        File file = new File("filename.txt");

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
     * @param arrayColumns String array that was splited from the read line
     * @return the string line with the desired values removing Volume and Adj Close from the list
     */
    private static String parseValidDataOnly(String[] arrayColumns) {
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

    private static ArrayList<ArrayList<Integer>> detectEngulfing(ArrayList<String> list) {
        ArrayList<Integer> bullishList = new ArrayList<>();
        ArrayList<Integer> bearishList = new ArrayList<>();

        // We skip first index because that's the category tables
        for (int index = 2; index < list.size(); index++) {
            String[] previewsDay = list.get(index - 1).split(",");
            String[] currentDay = list.get(index).split(",");

            double O1 = Double.parseDouble(previewsDay[Lables.OPEN.val()]);
            double C1 = Double.parseDouble(previewsDay[Lables.CLOSE.val()]);
            double O2 = Double.parseDouble(currentDay[Lables.OPEN.val()]);
            double C2 = Double.parseDouble(currentDay[Lables.CLOSE.val()]);
            double H1 = Double.parseDouble(previewsDay[Lables.HIGH.val()]);
            double L1 = Double.parseDouble(previewsDay[Lables.LOW.val()]);
            double H2 = Double.parseDouble(currentDay[Lables.HIGH.val()]);
            double L2 = Double.parseDouble(currentDay[Lables.LOW.val()]);

            if (bullishEngulfing(O1, C1, O2, C2, H1, L1, H2, L2)) {
                BullishEngulfing++;
                bullishList.add(index);
            }
            if (bearishEngulfing(O1, C1, O2, C2, H1, L1, H2, L2)) {
                BearishEngulfing++;
                bearishList.add(index);
            }
        }

        ArrayList<ArrayList<Integer>> arrayOfArray = new ArrayList<>();

        arrayOfArray.add(bullishList);
        arrayOfArray.add(bearishList);

        System.out.println("Bull engulfing found: " + BullishEngulfing);
        System.out.println("Bear engulfing found: " + BearishEngulfing);

        return arrayOfArray;
    }

    /**
     * @param O1 OPENING FROM PREVIOUS DAY
     * @param C1 CLOSING FROM PREVIOUS DAY
     * @param O2 OPENING FROM NEXT DAY OF PREVIOUS DAY
     * @param C2 CLOSING FROM NEXT DAY OF PREVIOUS DAY
     * @return Bullish Engulfing
     */
    private static boolean bullishEngulfing(double O1, double C1, double O2, double C2, double H1, double L1, double H2, double L2) {
        return ((O1 >= C1) && (O2 <= C2) && (O2 <= C1) && (C2 >= O1) && (L1 >= L2) && (H2 >= H1));
    }

    /**
     * @param O1 OPENING FROM PREVIOUS DAY
     * @param C1 CLOSING FROM PREVIOUS DAY
     * @param O2 OPENING FROM NEXT DAY OF PREVIOUS DAY
     * @param C2 CLOSING FROM NEXT DAY OF PREVIOUS DAY
     * @return Bearish Engulfing
     */
    private static boolean bearishEngulfing(double O1, double C1, double O2, double C2, double H1, double L1, double H2, double L2) {
        return ((C1 <= O2) && (O1 >= C2) && (O1 <= C1) && (O2 >= C2) && (L1 >= L2) && (H2 >= H1));
    }
}