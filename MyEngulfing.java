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

        if (Settings.consoleOutput) {
            String tableTitle;
            if (isBullish) {
                tableTitle = "\nBULL: " + Settings.companyName;
                System.out.print(tableTitle);
            } else {
                tableTitle = "\nBEAR: " + Settings.companyName;
                System.out.print(tableTitle);
            }

        }

        CsvFormat rowData = new CsvFormat(listOfTenDays);

        ArrayList<String> dates = rowData.getDates();
        ArrayList<String> closingPricing = rowData.getClosingPricing();

        //Append content
        super.appendText(rowData.cvsOutputPercentage(percentageOfList));
        if (Settings.consoleOutput)
            rowData.printTable(dates, closingPricing, percentageOfList);
    }

    @Override
    void doMore(boolean isBullish) {
        // Writes file if cvs is Active
        this.getTextManipulation().setAllowCreateFile(Settings.outputCsvFile);
        if (this.getTextManipulation().isAppendActive() && this.getTextManipulation().getAllowCreateFile()) {
            String fileName;

            try {
                fileName = getEnglfName(isBullish);
                this.getTextManipulation().writeFile(fileName);

                // Calculates the average of all the engulfing found
                if (Settings.outputStockAnalysis){
                    try {
                        TextManipulation stockAnalysis = new TextManipulation(true);
                        StockAnalysis analysis = new StockAnalysis(this.getTextManipulation().toArrayList());
                        // Add text to write
                        stockAnalysis.appendText(CsvFormat.daysCvsFormat("Company,Vol"));
                        stockAnalysis.appendText(analysis.getAveragePerDay());

                        fileName = getEnglfName(isBullish);
                        // Allows us to create file
                        stockAnalysis.setAllowCreateFile(true);
                        stockAnalysis.writeFile(fileName + "_ANALYSIS_AVERAGE");
                        System.out.println(stockAnalysis.getFullTextContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getEnglfName(boolean isBullish){
        String fileName;
        if (isBullish) {
            fileName = Settings.companyName + "_BULLISH";
        } else {
            fileName = Settings.companyName + "_BEARISH";
        }
        return fileName;
    }
}
