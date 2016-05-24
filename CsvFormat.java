import java.util.ArrayList;

/**
 * Created by Josue on 4/23/2016.
 */
public class CsvFormat {
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> closingPricing = new ArrayList<>();

    public CsvFormat(ArrayList<String> rowList) {
        for (String row : rowList) {
            String[] rowData = Engulfing.splitRow(row);
            dates.add(rowData[Lables.DATE.val()]);
            closingPricing.add(rowData[Lables.CLOSE.val()]);
        }
    }

    public ArrayList<String> getDates() {
        return this.dates;
    }

    public ArrayList<String> getClosingPricing() {
        return this.closingPricing;
    }

    public String cvsOutputPercentage(ArrayList<String> percentageOfList) {
        String date = this.dates.get(0);
        percentageOfList.set(0, date);
        int size = percentageOfList.size() - 1;

        String formatString = "";
        for (int day = 0; day <= size; day++) {
            if (day != size) {
            	formatString += percentageOfList.get(day) + ",";
            } else {
                formatString += percentageOfList.get(day) + "\n";
            }
        }
        return formatString;
    }

    public static String daysCvsFormat(String prevString) {
        String dayCvsFormat = prevString + " | ";
        for (int day = 1; day < 12; day++) {
            String dayString = "Day " + day;
            if (day != 12 - 1) {
                dayCvsFormat += dayString + "| ";
            } else {
                dayCvsFormat += dayString + "\n";
            }
        }
        return dayCvsFormat;
    }

    public void printTable(ArrayList<String> dates, ArrayList<String> closingPricing, ArrayList<String> percentageOfList) {
        String charLimits = "| %-15s";
        
        System.out.println();
        String titleDate = " date of engulfing ---> " + dates.get(1);
        System.out.println(titleDate);
        printDelimeters();
        System.out.format(charLimits, "**************");
        printDays();
        printDelimeters();
        System.out.format(charLimits, "Date");
        printRowCategory(dates);
        System.out.format(charLimits, "Price");
        printRowCategory(closingPricing);
        System.out.format(charLimits, "Profit(%)");
        printRowCategory(percentageOfList);
        printDelimeters();
    }

    private void printRowCategory(ArrayList<String> category) {
        String charLimits = "| %-15s";
        for (String aCategory : category) {
            System.out.format(charLimits, aCategory);
        }
        System.out.print("|\n");
    }

    private void printDelimeters() {
        for (int day = 0; day < 12; day++) {
            System.out.print("+----------------");
        }
        System.out.print("+\n");
    }

    private void printDays() {
        String charLimits = "| %-15s";

        for (int day = 0; day < 12; day++) {
            String dayString = "Day " + day;
            System.out.format(charLimits, dayString);
        }
        System.out.print("|\n");
    }

    private String listToCSVformat(ArrayList<String> list) {
        String formattedString = "";
        for (int count = 0; count < list.size(); count++) {
            if (count == list.size() - 1) {
                formattedString += list.get(count) + "\n";
            } else {
                formattedString += list.get(count) + ",";
            }
        }
        return formattedString;
    }
}
