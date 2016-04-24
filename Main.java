import java.util.ArrayList;

public class Main {
    // Change it as your path indicates

    public static void main(String[] args) {
        MyConsoleMenu menu = new MyConsoleMenu();

        DownloadCompanyCsv companyCsv = new DownloadCompanyCsv(menu.getCompanyName());
        StaticValues.companyName = companyCsv.getCompanyName();
        String originalCSV = menu.getFileName();
        if(StaticValues.companyName == null){
            StaticValues.companyName = originalCSV.substring(0, originalCSV.indexOf("."));
        }

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

            if (bearish != null) {
                engulfing.loopEngulfing(sortedCSV, bearish, false);
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