/**
 * Created by Josue on 4/23/2016.
 */
public class TextString {
    private String fullTextContent = "";
    private boolean appendTextFile;

    TextString() {
        this.appendTextFile = false;
    }

    TextString(boolean appendTextFile) {
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
}
