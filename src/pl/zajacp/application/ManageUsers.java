package pl.zajacp.application;

import pl.zajacp.application.helper.ManageHelper;
import pl.zajacp.model.User;

import java.util.Arrays;
import java.util.Scanner;

public class ManageUsers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        crudMenu(sc);
    }

    private static void crudMenu(Scanner sc) {
        int choice = ManageHelper.getInputInt(sc, ManageHelper.CRUD_SELECTION, 5);
        switch (choice) {
            case 1:
                addUser(sc);
                break;
            case 2:
                editUser(sc);
                break;
            case 3:
                deleteUser(sc);
                break;
            case 4:
                try {
                    System.out.println("\n All records from the database:");
                    Arrays.stream(User.loadAll()).forEach(x -> System.out.println(x));
                } catch (NullPointerException e) {
                    System.out.println("\n There are no records in the database.");
                }
                System.out.println("");
                crudMenu(sc);
                break;
        }
    }

    private static void addUser(Scanner sc) {
        System.out.println("\nAdding new user to the database:");

        Object[] input = getUserInput(sc);
        User user = new User();
        user.setUsername((String) input[0]);
        user.setEmail((String) input[1]);
        user.setPassword((String) input[2]);
        user.setUser_group_id((Integer) input[3]);

        System.out.println();
        if (user.save()) {
            System.out.println("User added successfully to the database.");
        }

        int again = ManageHelper.getInputInt(sc, ManageHelper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                addUser(sc);
                break;
            case 2:
                System.out.println("_________________________________\n");
                crudMenu(sc);
                break;
        }
    }

    private static void editUser(Scanner sc) {
        System.out.println("\nEditing existing User in the database:");
        int userId = ManageHelper.getInputInt(sc, "Type id of user to be edited: ", Integer.MAX_VALUE);
        User user = User.loadById(userId);
        if (user != null) {
            Object[] input = getUserInput(sc);
            user.setUsername((String) input[0]);
            user.setEmail((String) input[1]);
            user.setPassword((String) input[2]);
            user.setUser_group_id((Integer) input[3]);
            if (user.save()) {
                System.out.println("User edition saved successfully to the database.");
            }
        }

//        System.out.println();

        int again = ManageHelper.getInputInt(sc, ManageHelper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                editUser(sc);
                break;
            case 2:
                System.out.println("_________________________________\n");
                crudMenu(sc);
                break;
        }
    }

    private static void deleteUser(Scanner sc) {

        System.out.println("\nDeleting User from the database:");
        int userId = ManageHelper.getInputInt(sc, "Type id of user to be deleted: ", Integer.MAX_VALUE);
        User user = User.loadById(userId);
        if (user != null && user.delete()) {
            System.out.println("User successfully deleted from the database.");
        }

//        System.out.println();

        int again = ManageHelper.getInputInt(sc, ManageHelper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                deleteUser(sc);
                break;
            case 2:
                System.out.println("_________________________________\n");
                crudMenu(sc);
                break;
        }
    }

    private static Object[] getUserInput(Scanner sc) {
        System.out.print("Type username: ");
        String username = sc.next();
        System.out.print("Type email: ");
        String email = sc.next();
        while (!User.checkEmail(email)) {
            System.out.println("Incorrect email.");
            System.out.print("Type email: ");
            email = sc.next();
        }
        System.out.print("Type password: ");
        String password = sc.next();
        System.out.print("Type id of group the user belongs to:  ");
        while (!sc.hasNextInt()) {
            System.out.println("Incorrect user group id.");
            System.out.print("Type id of group the user belongs to:  ");
            sc.next();
        }
        int user_group_id = sc.nextInt();

        Object[] inputs = new Object[4];
        inputs[0] = username;
        inputs[1] = email;
        inputs[2] = password;
        inputs[3] = user_group_id;
        return inputs;
    }
}

