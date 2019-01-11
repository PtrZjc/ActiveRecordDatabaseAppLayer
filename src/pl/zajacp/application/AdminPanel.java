package pl.zajacp.application;

import pl.zajacp.application.programs.*;

import java.util.Scanner;

public class AdminPanel {

    public static void main(String[] args) {
        welcome();
    }

    private static final String ADMIN_PANEL_SELECT = "\nChoose which program to run:\n 1) Manage the users\n 2) Manage the exercises\n 3) Manage the groups \n 4) Assign exercise to users \n 5) Quit \nYour choice (number): ";

    public static void welcome(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to administrative panel.");
        selectProgram(sc);
        sc.close();
    }
    public static void selectProgram(Scanner sc) {

        int choice = Helper.getInputInt(sc, ADMIN_PANEL_SELECT, 5);

        switch (choice) {
            case 1:
                ManageUsers.crudMenu(sc);
                break;
            case 2:
                ManageExercises.crudMenu(sc);
                break;
            case 3:
                ManageUserGroups.crudMenu(sc);
                break;
            case 4:
                ManageSolutions.crudMenu(sc);
            case 5:
                break;
        }
    }
}