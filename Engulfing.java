import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Josue on 4/22/2016.
 */
public abstract class Engulfing {
    private ArrayList<Integer> bullishIndexList = new ArrayList<>();
    private ArrayList<Integer> bearishIndexList = new ArrayList<>();
    private long bullishCount = 0;
    private long bearishCount = 0;
    public boolean allowAppendText = false;
    private TextManipulation textManipulation = new TextManipulation();

    Engulfing(ArrayList<String> list) {
        if (list.get(0).split(",").length >= 5) {
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

                if (isBullish(O1, C1, O2, C2, H1, L1, H2, L2)) {
                    this.bullishCount++;
                    this.bullishIndexList.add(index);
                }
                if (isBearish(O1, C1, O2, C2, H1, L1, H2, L2)) {
                    this.bearishCount++;
                    this.bearishIndexList.add(index);
                }
            }
        } else {
            throw new Error("List is null or has less than 5 elements");
        }
    }

    public void setAllowAppendText(boolean allowAppendText) {
        this.allowAppendText = allowAppendText;
        textManipulation.setappendTextFile(this.allowAppendText);
    }

    public boolean isAppendTextAllowed() {
        return this.allowAppendText;
    }

    /**
     * @param O1 OPENING FROM PREVIOUS DAY
     * @param C1 CLOSING FROM PREVIOUS DAY
     * @param O2 OPENING FROM NEXT DAY OF PREVIOUS DAY
     * @param C2 CLOSING FROM NEXT DAY OF PREVIOUS DAY
     * @return Bullish Engulfing
     */
    private boolean isBullish(double O1, double C1, double O2, double C2, double H1, double L1, double H2, double L2) {
        return ((O1 >= C1) && (O2 <= C2) && (O2 <= C1) && (C2 >= O1) && (L1 >= L2) && (H2 >= H1));
    }

    /**
     * @param O1 OPENING FROM PREVIOUS DAY
     * @param C1 CLOSING FROM PREVIOUS DAY
     * @param O2 OPENING FROM NEXT DAY OF PREVIOUS DAY
     * @param C2 CLOSING FROM NEXT DAY OF PREVIOUS DAY
     * @return Bearish Engulfing
     */
    private static boolean isBearish(double O1, double C1, double O2, double C2, double H1, double L1, double H2, double L2) {
        return ((C1 <= O2) && (O1 >= C2) && (O1 <= C1) && (O2 >= C2) && (L1 >= L2) && (H2 >= H1));
    }

    /**
     * Gets the list of indexes all bullish
     *
     * @return bullish list (ArrayList of Integers) or null
     */
    public ArrayList<Integer> getBullishIndexList() {
        if (this.bullishIndexList.size() > 0) {
            return this.bullishIndexList;
        } else {
            return null;
        }
    }

    /**
     * Gets the list of indexes all bearish
     *
     * @return bearish list (ArrayList of Integers) or null
     */
    public ArrayList<Integer> getBearishIndexList() {
        if (this.bearishIndexList.size() > 0) {
            return this.bearishIndexList;
        } else {
            return null;
        }
    }

    public long getBullishCount() {
        return this.bullishCount;
    }

    public long getBearishCount() {
        return this.bearishCount;
    }

    ArrayList<String> grabTenDaysAfter(ArrayList<String> list, int startIndex) {
        ArrayList<String> newList = new ArrayList<>();
        // index 0 will be our engulfing the next 10 will be our data needed
        for (int loops = 0; loops < 11; loops++) {
            newList.add(list.get(startIndex));
            startIndex++;
        }
        return newList;
    }

    public ArrayList<String> getProfitPercentage(ArrayList<String> listOfTenDates, boolean isBullish) {
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

    private String doProfitOperation(String firstDayAfterEngulfing, String currentDay, boolean isBullish) {
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

    public static String[] splitRow(String row) {
        return row.split(",");
    }

    public abstract void doIt(int index, ArrayList<String> sortedCVS, boolean isBullish);

    public void loopEngulfing(ArrayList<String> sortedCVS, ArrayList<Integer> engulfingTypeList, boolean isBullish) {
        textManipulation = new TextManipulation(this.allowAppendText);
        textManipulation.appendText(CsvFormat.daysCvsFormat("Date"));
//        int o = 0;
        for (int index : engulfingTypeList) {
            this.doIt(index, sortedCVS, isBullish);
//            if(o == 3){
//                break;
//            }
//            o++;
        }

        this.doMore(isBullish);
    }

    public TextManipulation getTextManipulation() {
        return this.textManipulation;
    }

    abstract void doMore(boolean isBullish);

    public void appendText(String contentToAppend) {
        this.textManipulation.appendText(contentToAppend);
    }

}
