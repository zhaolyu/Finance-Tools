import java.util.ArrayList;

public class Main {
    static MyEngulfing engulfing;
    // Change it as your path indicates
    public static void main(String[] args) {
        MyConsoleMenu menu = new MyConsoleMenu(); 

        DownloadCompanyCsv companyCsv = new DownloadCompanyCsv(menu.getCompanyName());
        Settings.companyName = companyCsv.getCompanyName();
        String originalCSV = menu.getFileName();
        if(Settings.companyName == null){
            Settings.companyName = originalCSV.substring(0, originalCSV.indexOf("."));
        }

        CsvReaderWriter csvFile = new CsvReaderWriter(originalCSV, true);
        ArrayList<String> lines = csvFile.readLinesFromFile();

        if (lines != null) {
            // Sort from oldest to newest
            ArrayList<String> sortedCSV = sortFromOldestToNewest(lines);
            // Detect engulfing
            engulfing = new MyEngulfing(sortedCSV);
            engulfing.setAllowAppendText(Settings.allowAppendingText);

            ArrayList<Integer> bullishList = engulfing.getBullishIndexList();
            ArrayList<Integer> bearishList = engulfing.getBearishIndexList();
            
            System.out.println("Bullish engulfings found: " + engulfing.getBullishCount());
            System.out.println("Bearish engulfings found: " + engulfing.getBearishCount());
            
            //Output engulfings
            if (bullishList != null) {
                engulfing.loopEngulfing(sortedCSV, bullishList, true);
            }
            
            if (bearishList != null) {
                engulfing.loopEngulfing(sortedCSV, bearishList, false);
            }
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