import java.util.ArrayList;

/**
 * Created by Josue on 4/23/2016.
 */
public class MyEngulfing extends Engulfing {
    MyEngulfing(ArrayList<String> list) {
        super(list);
    }

    @Override
    public void doIt(int index, ArrayList<String> sortedCVS, boolean isBullish) {
        ArrayList<String> listOfTenDays = grabTenDaysAfter(sortedCVS, index);
        ArrayList<String> percentageOfList = getProfitPercentage(listOfTenDays, isBullish);

        if (StaticValues.consoleOutput) {
            String tableTitle;
            if (isBullish) {
                tableTitle = "\nBULL: " + StaticValues.companyName;
                System.out.print(tableTitle);
            } else {
                tableTitle = "\nBEAR: " + StaticValues.companyName;
                System.out.print(tableTitle);
            }

        }

        CsvFormat rowData = new CsvFormat(listOfTenDays);

        ArrayList<String> dates = rowData.getDates();
        ArrayList<String> closingPricing = rowData.getClosingPricing();
        StaticValues.csvFileString.appendText(rowData.cvsOutputPercentage(percentageOfList));

        if (StaticValues.consoleOutput)
            rowData.printTable(dates, closingPricing, percentageOfList);
    }
}
