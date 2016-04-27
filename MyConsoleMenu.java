/**
 * Created by Josue on 4/23/2016.
 */
public class MyConsoleMenu extends ConsoleMenu  {
    MyConsoleMenu() {
        System.out.print("Welcome to MAC Sun Financial Tools \nOur tools can help you find Bullish and Bearish engulfings. \n\n");

        do {
            System.out.println("\nOptions:");
            System.out.println("-------------------------------------");
            System.out.println("1) Download Csv file from Yahoo");
            System.out.println("2) Enter File Path from computer \n");
            this.option = inputInteger("Pick an option: ", "You haven't chosen any option ");
        } while (this.option > 2)

        firstOption();

        do {
            System.out.println("\nOutput Options:");
            System.out.println("-------------------------------------------------------------");
            System.out.println("1) Print table in console of the next 10 days");
            System.out.println("2) Create Csv File of the next 10 days profit %");
            System.out.println("3) Print table in console and Write Csv file of the 10 days profit %");
            this.outputOption = inputInteger("Pick an output Option from above:", "You haven't chosen any option");
        } while( this.outputOption > 3)

        secondOption();
    }

    @Override
    public void firstOption() {
        switch (this.option) {
            case 1:
                this.companyName = inputString("Enter acronyms of the company \ni.e (BAC) for Bank of America Corp: ",
                        "Enter a valid acronyms").toUpperCase();
                this.fileName = this.companyName.trim().toUpperCase();
                break;
            case 2:
                this.fileName = inputString("\nWrite the file path or the file name including the file type (example.extension): ",
                        "You must input a file name").trim();
                if (!this.fileName.contains(".")){
                    this.fileName += ".csv";
                }
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
                Settings.consoleOutput = true;
                break;
            case 2:
                Settings.outputCsvFile = true;
                break;
            case 3:
                Settings.consoleOutput = true;
                Settings.outputCsvFile = true;
                break;
            default:
                Settings.consoleOutput = true;
                break;
        }
    }
}
