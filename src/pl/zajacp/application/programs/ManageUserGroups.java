package pl.zajacp.application.programs;

import pl.zajacp.application.AdminPanel;
import pl.zajacp.application.Helper;
import pl.zajacp.model.UserGroup;

import java.util.Arrays;
import java.util.Scanner;

public class ManageUserGroups {

    public static void crudMenu() {
        int choice = Helper.getInputInt( Helper.CRUD_SELECTION, 5);
        switch (choice) {
            case 1:
                addUserGroup();
                break;
            case 2:
                editUserGroup();
                break;
            case 3:
                deleteUserGroup();
                break;
            case 4:
                try {
                    System.out.println("\n All user groups present in the database:");
                    Arrays.stream(UserGroup.loadAll()).forEach(x -> System.out.println(x));
                } catch (NullPointerException e) {
                    System.out.println("\n There are no records in the database.");
                }
                System.out.println();
                crudMenu();
                break;
            case 5:
                AdminPanel.selectProgram();
                break;
        }
    }

    private static void addUserGroup() {
        System.out.println("\nAdding new user group to the database:");

        UserGroup userGroup = new UserGroup();
        userGroup.setName(getUserGroupInput());

        System.out.println();
        if (userGroup.save()) {
            System.out.println("UserGroup added successfully to the database.");
        }
        runAgain( 1);
    }

    private static void editUserGroup() {
        System.out.println("\nEditing existing user group in the database:");
        int userGroupId = Helper.getInputInt( "Type id of user group to be edited:", Integer.MAX_VALUE);
        UserGroup userGroup = UserGroup.loadById(userGroupId);
        if (userGroup != null) {
            userGroup.setName(getUserGroupInput());
            if (userGroup.save()) {
                System.out.println("User group edition saved successfully to the database.");
            }
        }
        runAgain( 2);
    }

    private static void deleteUserGroup() {

        System.out.println("\nDeleting user group from the database:");
        int userGroupId = Helper.getInputInt( "Type id of user group to be deleted: ", Integer.MAX_VALUE);
        UserGroup userGroup = UserGroup.loadById(userGroupId);
        if (userGroup != null && userGroup.delete()) {
            System.out.println("User group successfully deleted from the database.");
        }
        runAgain( 3);
    }

    private static String getUserGroupInput() {
        System.out.print("Type user group name: ");
        Helper.sc.nextLine();
        return Helper.sc.nextLine();
    }

    private static void runAgain(int choice) {
        int again = Helper.getInputInt( Helper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        addUserGroup();
                        break;
                    case 2:
                        editUserGroup();
                        break;
                    case 3:
                        deleteUserGroup();
                        break;
                }
                break;
            case 2:
                System.out.println();
                crudMenu();
                break;
        }
    }
}

