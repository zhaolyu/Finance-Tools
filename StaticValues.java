import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Josue on 4/24/2016.
 */
public class StaticValues {
    static String companyName;
    static boolean consoleOutput = false;
    static TextString csvFileString = new TextString();

    static void writeFile(String content, String outputFileName) {
        String csvFileName = outputFileName + ".csv";
        File file = new File(csvFileName);

        // if file doesnt exists, then create it
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println(csvFileName + " created.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
