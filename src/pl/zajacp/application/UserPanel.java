package pl.zajacp.application;

import pl.zajacp.application.helper.ManageHelper;
import pl.zajacp.model.Exercise;
import pl.zajacp.model.Solution;
import pl.zajacp.model.User;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserPanel {
    private static Integer userID;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        login(sc);
    }

    public static void login(Scanner sc) {
        UserPanel.userID = ManageHelper.getInputInt(sc, "\nType ID to login: ", Integer.MAX_VALUE);
        User currentUser = User.loadById(userID);
        if (currentUser == null) {
            runAgain(sc, 1);
        } else {
            userMenu(sc);
        }
    }

    private static void userMenu(Scanner sc) {
        int choice = ManageHelper.getInputInt(sc, ManageHelper.USER_PANEL_SELECT, 4);
        switch (choice) {
            case 1:
                addSolution(sc);
                break;
            case 2:
                viewSolution(sc);
                break;
            case 3:
        }
    }

    private static void addSolution(Scanner sc) {
        Exercise[] exercises = Exercise.loadAll();
        Set<Integer> solutionID = new HashSet<>();

        Arrays.stream(Solution.loadAllByUserId(userID)).forEach(x -> solutionID.add(x.getExercise_id()));
        System.out.println("\nList of exercises without the solution:\n");
        Arrays.stream(exercises).filter(x -> !solutionID.contains(x.getId())).forEach(System.out::println);
        System.out.println();

        int exerciseId = ManageHelper.getInputInt(sc, "Type id of exercise to assign the solution to: ", Integer.MAX_VALUE);
        System.out.println("Type in the solution description: ");
        sc.nextLine();
        String description = sc.nextLine();

        Solution solution = new Solution();
        solution.setExercise_id(exerciseId);
        solution.setUser_id(userID);
        solution.setDescription(description);

        if (solution.save()) {
            System.out.println("Solution successfully saved in the database. ");
        }
        runAgain(sc, 2);
    }

    private static void viewSolution(Scanner sc) {
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
        runAgain(sc, 3);
    }

    private static void runAgain(Scanner sc, int choice) {
        int again = ManageHelper.getInputInt(sc, ManageHelper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        login(sc);
                        break;
                    case 2:
                        addSolution(sc);
                        break;
                    case 3:
                        viewSolution(sc);
                        break;
                }
                break;
            case 2:
                switch (choice) {
                    case 1:
                        break;
                    case 2:
                    case 3:
                        System.out.println("_________________________________\n");
                        userMenu(sc);
                        break;
                }
                break;

        }
    }


}
