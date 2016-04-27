import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Josue on 4/23/2016.
 */
public class TextManipulation {
    private String fullTextContent = "";
    private boolean appendTextFile;
    private boolean allowCreateFile;
    
    TextManipulation() {
        this.appendTextFile = false;
    }

    TextManipulation(boolean appendTextFile) {
        this.appendTextFile = appendTextFile;
    }

    public void setappendTextFile(boolean appendText) {
        this.appendTextFile = appendText;
    }

    public void appendText(String content) {
        if (this.appendTextFile) {
            this.fullTextContent += content;
//            System.out.println(this.fullTextContent);
        }
    }

    public boolean isAppendActive() {
        return this.appendTextFile;
    }

    public String getFullTextContent() {
        return this.fullTextContent;
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, this.fullTextContent.split("\n"));
        return arrayList;
    }

    public void setAllowCreateFile(boolean allowCreateFile){
        this.allowCreateFile = allowCreateFile;    
    }
    
    public boolean getAllowCreateFile(){
        return this.allowCreateFile;    
    }
    
    public void writeFile(String outputFileName) throws IOException {
        if(this.allowCreateFile){
            String csvFileName = outputFileName + ".csv";
            File file = new File(csvFileName);
            
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.fullTextContent);
            bw.close();

            System.out.println(csvFileName + " created.");
        }
    }
}
