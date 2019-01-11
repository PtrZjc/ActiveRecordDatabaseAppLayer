package pl.zajacp.application.programs;

import pl.zajacp.application.AdminPanel;
import pl.zajacp.application.Helper;
import pl.zajacp.model.Exercise;
import pl.zajacp.model.Solution;
import pl.zajacp.model.User;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ManageSolutions {

    private static final String SOLUTION_SELECTION = "Pick one of the options:\n 1) Assign exercise to user\n 2) View exercises of chosen user\n 3) Unassign exercise (and delete solution)\n 4) Go back \nYour choice (number): ";

    public static void crudMenu() {
        int choice = Helper.getInputInt( SOLUTION_SELECTION, 4);
        switch (choice) {
            case 1:
                addSolution();
                break;
            case 2:
                viewSolution();
                break;
            case 3:
                deleteSolution();
                break;
            case 4:
                AdminPanel.selectProgram();
                break;
        }
    }

    private static void addSolution() {
        try {
            System.out.println("\nShowing users present in the database:\n");
            Arrays.stream(User.loadAll()).forEach(x -> System.out.println(x));
        } catch (NullPointerException e) {
            System.out.println("\n There are no users in the database. Exercise may not be assigned.");
            System.out.println();
            crudMenu();
            return;
        }

        System.out.println();
        int userId = Helper.getInputInt( "Type id of user to assign the exercise to: ", Integer.MAX_VALUE);

        try {
            System.out.println("\nShowing exercises present in the database:\n");
            Arrays.stream(Exercise.loadAll()).forEach(x -> System.out.println(x));
        } catch (NullPointerException e) {
            System.out.println("\n There are no exercises in the database. Exercise may not be assigned.");
            System.out.println();
            crudMenu();
            return;
        }

        int exerciseId = Helper.getInputInt( "\nType id of exercise for assignment: ", Integer.MAX_VALUE);

        Solution solution = new Solution();
        solution.setUser_id(userId);
        solution.setExercise_id(exerciseId);

        if (solution.save()) {
            System.out.println("Exercise successfully assigned to the user.");
        }

        runAgain( 1);
    }

    private static void viewSolution() {
        Map<Integer, Integer> userExercises = new TreeMap<>();
        for (Solution solution : Solution.loadAll()) {
            int userId = solution.getUser_id();
            userExercises.putIfAbsent(userId, 0);
            userExercises.put(userId, userExercises.get(userId) + 1);
        }
        userExercises.entrySet().stream().forEach(ManageSolutions::printUserExercises);

        int userId = Helper.getInputInt( "\nType id of user to show his assigned exercises: ", Integer.MAX_VALUE);
        if (User.loadById(userId) != null) {
            System.out.println("Showing excercises of user " + userId + ": ");
            Arrays.stream(Solution.loadAllByUserId(userId)).forEach(x -> {
                if (x.getDescription() != null) {
                    System.out.print("  SOLVED: ");
                } else {
                    System.out.print("UNSOLVED: ");
                }
                System.out.println(x);
            });
        }
        System.out.println();

        runAgain( 2);
    }

    private static void deleteSolution() {

        Arrays.stream(Solution.loadAll()).forEach(System.out::println);

        System.out.println("\nUnassign exercise from user: ");
        int solutionId = Helper.getInputInt( "Type id of exercise to be deleted: ", Integer.MAX_VALUE);
        Solution solution = Solution.loadById(solutionId);
        if (solution != null && solution.delete()) {
            System.out.println("User solution successfully deleted from the database.");
        }
        runAgain( 3);
    }

    private static void runAgain(int choice) {
        int again = Helper.getInputInt( Helper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        addSolution();
                        break;
                    case 2:
                        viewSolution();
                        break;
                    case 3:
                        deleteSolution();
                        break;
                }
                break;
            case 2:
                System.out.println();
                crudMenu();
                break;
        }
    }

    private static void printUserExercises(Map.Entry<Integer, Integer> x) {
        if (x.getValue() > 0) {
            System.out.println(
                    new StringBuilder().append("User id: ")
                            .append(x.getKey())
                            .append(" (")
                            .append(User.loadById(x.getKey()).getUsername())
                            .append(") has ")
                            .append(x.getValue())
                            .append(" exercises assigned.")
                            .toString());
        }
    }
}
