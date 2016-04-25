/**
 * Created by Josue on 4/23/2016.
 */
public class MyConsoleMenu extends ConsoleMenu {
    private Engulfing cvsOutputEngulfing;

    MyConsoleMenu() {
        System.out.print("Welcome To Stack Tools: Find Bullish and Bearish Engulfing\n\n");

        System.out.println("1.- Download Csv");
        System.out.println("2.- Enter Path");
        this.option = inputInteger("Pick an option: ", "You haven't chosen any option");
        firstOption();
        System.out.println("1.- Print table in console of the next 10 days");
        System.out.println("2.- Create CVS File of the next 10 days profit %");
        System.out.println("3.- Print table in console and Write CVS file of the 10 days profit %");
        this.outputOption = inputInteger("Pick an output Option: ", "You haven't chosen any option");
        secondOption();
    }

    @Override
    public void firstOption() {
        switch (this.option) {
            case 1:
                this.companyName = inputString("Enter acronyms of the company example: \n\t(BAC) for Bank of America Corporation",
                        "Enter a valid acronyms").toUpperCase();
                this.fileName = this.companyName.trim().toUpperCase();
                break;
            case 2:
                this.fileName = inputString("\nWrite the path or the file name including the file type (example.extension): ",
                        "You must input a file name").trim();
                break;
            default:
                this.fileName = inputString("\nWrite the path or the file name including the file type (example.extension)",
                        "You must input a file name").trim();
                break;
        }
    }

    @Override
    public void secondOption() {
        switch (this.outputOption) {
            case 1:
                StaticValues.consoleOutput = true;
                break;
            case 2:
                StaticValues.outputCsvFile = true;
                break;
            case 3:
                StaticValues.consoleOutput = true;
                StaticValues.outputCsvFile = true;
                break;
            default:
                StaticValues.consoleOutput = true;
                break;
        }
    }
}
