import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Josue on 4/24/2016.
 */
public class DownloadCompanyCsv {
    private String companyName;
    private ArrayList<String> contentByLine;

    DownloadCompanyCsv(String companyName) {
        if (companyName != null) {
            this.companyName = companyName.trim().toUpperCase();

            YahooFinanceLinkGenerator yahooUrl = new YahooFinanceLinkGenerator(this.companyName);
            this.contentByLine = scrapeContent(yahooUrl.getFinanceHistoryLink());
            String dataToParse = findWordInContent("End Date");
//        System.out.println(dataToParse.trim());
            try {
                yahooUrl.setDate(parseStartEndDates(dataToParse.trim()));
                String downloadLink = yahooUrl.getFinanceDownloadLink();
                download(downloadLink, this.companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void download(String webURL, String cvsFileName) throws IOException {
        System.out.println("Starting to download...");
        String fileName = cvsFileName + ".csv"; //The file that will be saved on your computer
        URL link = new URL(webURL); //The file that you want to download

        //Code to download
        InputStream in = new BufferedInputStream(link.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(response);
        fos.flush();
        fos.close();
        System.out.println("Finished downloading");
    }

    private ArrayList<String> parseStartEndDates(String content) {
        ArrayList<String> dates = new ArrayList<>();
        String firstSubstring, secondSubstring;
        int firstIndex, secondIndex;

        firstIndex = content.indexOf("Start Date:");
        secondIndex = content.indexOf("End Date:");
//        System.out.println(firstIndex + ", " + secondIndex);

        firstSubstring = content.substring(firstIndex, secondIndex);
        secondSubstring = content.substring(secondIndex, content.length() - 1);
//        System.out.println(firstSubstring);
        String[] startDate = firstSubstring.split("<");
        String[] endDate = secondSubstring.split("<");
        dates.addAll(get1Selected2Inputs(startDate));
        dates.addAll(get1Selected2Inputs(endDate));
        return dates;
    }

    private ArrayList<String> get1Selected2Inputs(String[] list) {
        ArrayList<String> date = new ArrayList<>();
        int selectedMax = 1;
        int inputMax = 2;
        for (String item : list) {
//            System.out.println(dates);
            if (item.contains("selected")) {
                // This is the month
//                System.out.println(parseValueFromHTML(item));
                date.add(parseValueFromHTML(item));
                selectedMax--;
            }
            if (selectedMax == 0) {
                if (item.contains("input")) {
//                    System.out.println(parseValueFromHTML(item));
                    date.add(parseValueFromHTML(item));
                    inputMax--;
                    if (inputMax == 0)
                        break;
                }
            }
        }
        return date;
    }

    private String parseValueFromHTML(String htmlText) {
        String[] splittedText = htmlText.split(" ");
        String valueString = splittedText[splittedText.length - 1];

        ArrayList<Integer> quoIndex = new ArrayList<>();
        int index = valueString.indexOf("\"");
        int findOnlyIndex = 2;
        while (index >= 0) {
//            System.out.println(index);
            quoIndex.add(index);
            index = valueString.indexOf("\"", index + 1);
            findOnlyIndex--;
            if (findOnlyIndex == 0)
                break;
        }
        return valueString.substring(quoIndex.get(0) + 1, quoIndex.get(1));
    }

    public String findWordInContent(String word) {
        for (String line : this.contentByLine) {
            if (line.contains(word))
                return line.trim();
        }
        return null;
    }

    private ArrayList<String> scrapeContent(String webURL) {
        ArrayList<String> content = new ArrayList<>();
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL(webURL);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                content.add(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

        return content;
    }
}
