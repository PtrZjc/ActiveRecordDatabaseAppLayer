package pl.zajacp.application;

import pl.zajacp.model.Exercise;
import pl.zajacp.model.Solution;
import pl.zajacp.model.User;

import java.util.*;

public class UserPanel {

    private static Integer userID;
    private static final String USER_PANEL_SELECT = "User panel.\nPick one of the options:\n 1) Add solution to the exercise\n 2) View your exercises\n 3) Quit \nYour choice (number): ";

    public static void welcome() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to user panel.");
        login();
        Helper.sc.close();
    }

    public static void login() {
        UserPanel.userID = Helper.getInputInt("\nType ID to login: ", Integer.MAX_VALUE);
        User currentUser = User.loadById(userID);
        if (currentUser == null) {
            runAgain( 1);
        } else {
            userMenu();
        }
    }

    private static void userMenu() {
        int choice = Helper.getInputInt( USER_PANEL_SELECT, 4);
        switch (choice) {
            case 1:
                addSolution();
                break;
            case 2:
                viewSolution();
                break;
            case 3:
        }
    }

    private static void addSolution() {
        Exercise[] exercises = Exercise.loadAll();
        Set<Integer> solutionID = new HashSet<>();

        Arrays.stream(Solution.loadAllByUserId(userID)).forEach(x -> solutionID.add(x.getExercise_id()));
        System.out.println("\nList of exercises without the solution:\n");
        Arrays.stream(exercises).filter(x -> !solutionID.contains(x.getId())).forEach(System.out::println);
        System.out.println();

        int exerciseId = Helper.getInputInt( "Type id of exercise to assign the solution to: ", Integer.MAX_VALUE);
        System.out.println("Type in the solution description: ");
        Helper.sc.nextLine();
        String description = Helper.sc.nextLine();

        Solution solution = new Solution();
        solution.setExercise_id(exerciseId);
        solution.setUser_id(userID);
        solution.setDescription(description);

        if (solution.save()) {
            System.out.println("Solution successfully saved in the database. ");
        }
        runAgain( 2);
    }

    private static void viewSolution() {
        Map<Integer, Integer> userExercises = new TreeMap<>();

        System.out.println("List of your exercises:\n");
        Arrays.stream(Solution.loadAllByUserId(userID)).forEach(x -> {
            if (x.getDescription() != null) {
                System.out.print("  SOLVED: ");
            } else {
                System.out.print("UNSOLVED: ");
            }
            System.out.println(x);
        });
        System.out.println();
        userMenu();

    }

    private static void runAgain(int choice) {
        int again = Helper.getInputInt( Helper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        addSolution();
                        break;
                    case 3:
                        viewSolution();
                        break;
                }
                break;
            case 2:
                switch (choice) {
                    case 1:
                        break;
                    case 2:
                    case 3:
                        System.out.println();
                        userMenu();
                        break;
                }
                break;
        }
    }


}
