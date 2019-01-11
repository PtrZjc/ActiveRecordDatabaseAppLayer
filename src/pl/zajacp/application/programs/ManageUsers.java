package pl.zajacp.application.programs;

import pl.zajacp.application.AdminPanel;
import pl.zajacp.application.Helper;
import pl.zajacp.model.User;

import java.util.Arrays;
import java.util.Scanner;

public class ManageUsers {

    public static void crudMenu() {
        int choice = Helper.getInputInt( Helper.CRUD_SELECTION, 5);
        switch (choice) {
            case 1:
                addUser();
                break;
            case 2:
                editUser();
                break;
            case 3:
                deleteUser();
                break;
            case 4:
                try {
                    System.out.println("\n All users present in the database:");
                    Arrays.stream(User.loadAll()).forEach(x -> System.out.println(x));
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

    private static void addUser() {
        System.out.println("\nAdding new user to the database:");

        Object[] input = getUserInput();
        User user = new User();
        user.setUsername((String) input[0]);
        user.setEmail((String) input[1]);
        user.setPassword((String) input[2]);
        user.setUser_group_id((Integer) input[3]);

        System.out.println();
        if (user.save()) {
            System.out.println("User added successfully to the database.");
        }
        runAgain( 1);
    }

    private static void editUser() {
        System.out.println("\nEditing existing User in the database:");
        int userId = Helper.getInputInt( "Type id of user to be edited: ", Integer.MAX_VALUE);
        User user = User.loadById(userId);
        if (user != null) {
            Object[] input = getUserInput();
            user.setUsername((String) input[0]);
            user.setEmail((String) input[1]);
            user.setPassword((String) input[2]);
            user.setUser_group_id((Integer) input[3]);
            if (user.save()) {
                System.out.println("User edition saved successfully to the database.");
            }
        }
        runAgain( 2);

    }

    private static void deleteUser() {

        System.out.println("\nDeleting User from the database:");
        int userId = Helper.getInputInt( "Type id of user to be deleted: ", Integer.MAX_VALUE);
        User user = User.loadById(userId);
        if (user != null && user.delete()) {
            System.out.println("User successfully deleted from the database.");
        }
        runAgain( 3);
    }

    private static Object[] getUserInput() {
        System.out.print("Type username: ");
        String username = Helper.sc.next();
        System.out.print("Type email: ");
        String email = Helper.sc.next();
        while (!User.checkEmail(email)) {
            System.out.println("Incorrect email.");
            System.out.print("Type email: ");
            email = Helper.sc.next();
        }
        System.out.print("Type password: ");
        String password = Helper.sc.next();
        System.out.print("Type id of group the user belongs to:  ");
        while (!Helper.sc.hasNextInt()) {
            System.out.println("Incorrect user group id.");
            System.out.print("Type id of group the user belongs to:  ");
            Helper.sc.next();
        }
        int user_group_id = Helper.sc.nextInt();

        Object[] inputs = new Object[4];
        inputs[0] = username;
        inputs[1] = email;
        inputs[2] = password;
        inputs[3] = user_group_id;
        return inputs;
    }

    private static void runAgain(int choice) {
        int again = Helper.getInputInt( Helper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        editUser();
                        break;
                    case 3:
                        deleteUser();
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

