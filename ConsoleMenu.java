import java.util.Scanner;

/**
 * Created by Josue on 4/23/2016.
 */
abstract class ConsoleMenu {
    String fileName;
    int option;
    int outputOption;
    String companyName;

    ConsoleMenu() {

    }

    abstract void firstOption();

    abstract void secondOption();

    String getFileName() {
        return this.fileName;
    }

    String getCompanyName() {
        return this.companyName;
    }

    String inputString(String mainMessage, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mainMessage);
        String text = scanner.nextLine();
        if (text == null || text.length() == 0) {
            System.out.println(errorMessage);
            scanner.close();
            inputString(mainMessage, errorMessage);
        }

        return text;
    }

    int inputInteger(String mainMessage, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mainMessage);
        int option;
        String text = scanner.nextLine();
        if (text == null || text.length() == 0) {
            System.out.println(errorMessage);
            scanner.close();
            inputInteger(mainMessage, errorMessage);
        }

        try {
            option = Integer.parseInt(text.trim());
        } catch (Exception e) {
            return inputInteger(mainMessage, errorMessage);
        }

        return option;
    }
}
