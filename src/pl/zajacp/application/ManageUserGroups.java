package pl.zajacp.application;

import pl.zajacp.application.helper.ManageHelper;
import pl.zajacp.model.UserGroup;

import java.util.Arrays;
import java.util.Scanner;

public class ManageUserGroups {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        crudMenu(sc);
    }

    private static void crudMenu(Scanner sc) {
        int choice = ManageHelper.getInputInt(sc, ManageHelper.CRUD_SELECTION, 5);
        switch (choice) {
            case 1:
                addUserGroup(sc);
                break;
            case 2:
                editUserGroup(sc);
                break;
            case 3:
                deleteUserGroup(sc);
                break;
            case 4:
                try {
                    System.out.println("\n All user groups present in the database:");
                    Arrays.stream(UserGroup.loadAll()).forEach(x -> System.out.println(x));
                } catch (NullPointerException e) {
                    System.out.println("\n There are no records in the database.");
                }
                System.out.println();
                crudMenu(sc);
                break;
        }
    }

    private static void addUserGroup(Scanner sc) {
        System.out.println("\nAdding new user group to the database:");

        UserGroup userGroup = new UserGroup();
        userGroup.setName(getUserGroupInput(sc));

        System.out.println();
        if (userGroup.save()) {
            System.out.println("UserGroup added successfully to the database.");
        }
        runAgain(sc, 1);
    }

    private static void editUserGroup(Scanner sc) {
        System.out.println("\nEditing existing user group in the database:");
        int userGroupId = ManageHelper.getInputInt(sc, "Type id of user group to be edited:", Integer.MAX_VALUE);
        UserGroup userGroup = UserGroup.loadById(userGroupId);
        if (userGroup != null) {
            userGroup.setName(getUserGroupInput(sc));
            if (userGroup.save()) {
                System.out.println("User group edition saved successfully to the database.");
            }
        }
        runAgain(sc, 2);
    }

    private static void deleteUserGroup(Scanner sc) {

        System.out.println("\nDeleting user group from the database:");
        int userGroupId = ManageHelper.getInputInt(sc, "Type id of user group to be deleted: ", Integer.MAX_VALUE);
        UserGroup userGroup = UserGroup.loadById(userGroupId);
        if (userGroup != null && userGroup.delete()) {
            System.out.println("User group successfully deleted from the database.");
        }
        runAgain(sc, 3);
    }

    private static String getUserGroupInput(Scanner sc) {
        System.out.print("Type user group name: ");
        sc.nextLine();
        return sc.nextLine();
    }

    private static void runAgain(Scanner sc, int choice) {
        int again = ManageHelper.getInputInt(sc, ManageHelper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        addUserGroup(sc);
                        break;
                    case 2:
                        editUserGroup(sc);
                        break;
                    case 3:
                        deleteUserGroup(sc);
                        break;
                }
                break;
            case 2:
                System.out.println("_________________________________\n");
                crudMenu(sc);
                break;
        }
    }
}

