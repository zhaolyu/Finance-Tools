/**
 * Created by Josue on 4/23/2016.
 */
public class MyConsoleMenu extends ConsoleMenu {
    MyConsoleMenu() {
        System.out.print("Welcome To Stack Tools\n\n");

        System.out.print("Pick a Tools\n");
        System.out.println("1.- Find Bullish and Bearish Engulfing");
        this.option = inputInteger("Choose an option:", "You haven't chosen any option");
        System.out.print("Pick an Output Option\n");
        System.out.println("1.- Print table in console of the next 10 days");
        System.out.println("2.- Write CVS File of the next 10 days profit %");
        System.out.println("3.- Write a TEXT file of the next 10 days profit %");
        System.out.println("4.- Print table in console and Write CVS file of the 10 days profit %");
        this.outputOption = inputInteger("Pick the output Option you desire: ", "You haven't chosen any option");
        secondOption();
        this.fileName = inputString("\nWrite the path or the file name including the file type (example.extension)",
                "You must input a file name").trim();
    }

    @Override
    public void firstOption() {
        switch (this.option) {
            case 1:
                break;
            default:
                break;
        }
    }

    @Override
    public void secondOption() {
        switch (this.outputOption) {
            case 1:
                Main.consoleOutput = true;
                break;
            case 2:
                Main.csvFileString.setappendTextFile(true);
                break;
            case 3:
                Main.textFileString.setappendTextFile(true);
                break;
            case 4:
                Main.consoleOutput = true;
                Main.csvFileString.setappendTextFile(true);
                break;
            default:
                Main.consoleOutput = true;
                break;
        }
    }
}
