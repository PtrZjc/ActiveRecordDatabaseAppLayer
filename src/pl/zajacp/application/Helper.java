package pl.zajacp.application;

import java.util.Scanner;

public class Helper {

    public static Scanner sc;
    public static final String CRUD_SELECTION = "Pick one of the options:\n 1) Add\n 2) Edit\n 3) Delete\n 4) Show all records\n 5) Go back\nYour choice (number): ";
    public static final String ANOTHER_OPERATION = "Do you want to perform operation again?\n 1) Yes\n 2) No\nYour choice (number): ";

    public static int getInputInt(String message, int upperBound) {
        int pick = 0;
        boolean flag = false;
        do {
            if (flag) {
                System.out.println("Incorrect value.\n");
            }
            System.out.print(message);
            while (!Helper.sc.hasNextInt()) {
                System.out.println("Incorrect value.\n");
                System.out.print(message);
                Helper.sc.next();
            }
            pick = Helper.sc.nextInt();
            flag = true;
        } while (pick < 1 || pick > upperBound);
        return pick;

    }

}
