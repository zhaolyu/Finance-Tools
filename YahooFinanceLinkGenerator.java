import java.util.ArrayList;

/**
 * Created by Josue on 4/24/2016.
 */
public class YahooFinanceLinkGenerator {
    private String companyName;
    private ArrayList<String> date;

    YahooFinanceLinkGenerator(String companyName){
        this.companyName = companyName;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public String getFinanceDownloadLink() {
        return "http://real-chart.finance.yahoo.com/table.csv?s=" +
                this.companyName + "&d=" + (Integer.parseInt(this.date.get(3)) + 1) + "&e=" + this.date.get(4) + "&f=" +
                this.date.get(5) + "&g=d&a=" + (Integer.parseInt(this.date.get(0)) + 1) + "&b=" + this.date.get(1) + "&c=" +
                this.date.get(2) + "&ignore=.csv";
    }

    public String getFinanceHistoryLink() {
        return "https://finance.yahoo.com/q/hp?s=" + this.companyName + "+Historical+Prices";
    }
}
