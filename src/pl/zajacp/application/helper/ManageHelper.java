package pl.zajacp.application.helper;

import java.util.Scanner;

public class ManageHelper {

    public static final String CRUD_SELECTION = "Pick one of the options:\n 1) Add\n 2) Edit\n 3) Delete\n 4) Show all records\n 5) Quit\nYour choice (number): ";
    public static final String SOLUTION_SELECTION = "Pick one of the options:\n 1) Assign exercise to user\n 2) View exercises of chosen user\n 3) Unassign exercise (and delete solution)\n 4) Quit \nYour choice (number): ";
    public static final String ANOTHER_OPERATION = "Do you want to perform operation again?\n 1) Yes\n 2) No\nYour choice (number): ";

    public static int getInputInt(Scanner sc, String message, int upperBound) {
        int choice = 0;
        boolean flag = false;
        do {
            if (flag) {
                System.out.println("Incorrect value.\n");
            }
            System.out.print(message);
            while (!sc.hasNextInt()) {
                System.out.println("Incorrect value.\n");
                System.out.print(message);
                sc.next();
            }
            choice = sc.nextInt();
            flag = true;
        } while (choice < 1 || choice > upperBound);
        return choice;

    }

}
