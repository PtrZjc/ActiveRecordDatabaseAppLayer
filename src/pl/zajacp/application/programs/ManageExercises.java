package pl.zajacp.application.programs;

import pl.zajacp.application.AdminPanel;
import pl.zajacp.application.Helper;
import pl.zajacp.model.Exercise;

import java.util.Arrays;
import java.util.Scanner;

public class ManageExercises {

    public static void crudMenu() {
        int choice = Helper.getInputInt( Helper.CRUD_SELECTION, 5);
        switch (choice) {
            case 1:
                addExercise();
                break;
            case 2:
                editExercise();
                break;
            case 3:
                deleteExercise();
                break;
            case 4:
                try {
                    System.out.println("\n All exercises present in the database:");
                    Arrays.stream(Exercise.loadAll()).forEach(x -> System.out.println(x));
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

    private static void addExercise() {
        System.out.println("\nAdding new exercise to the database:");

        String[] input = getExerciseInput();
        Exercise exercise = new Exercise();
        exercise.setTitle(input[0]);
        exercise.setDescription(input[1]);

        System.out.println();
        if (exercise.save()) {
            System.out.println("Exercise added successfully to the database.");
        }
        runAgain( 1);
    }

    private static void editExercise() {
        System.out.println("\nEditing existing Exercise in the database:");
        int exerciseId = Helper.getInputInt( "Type id of exercise to be edited: ", Integer.MAX_VALUE);
        Exercise exercise = Exercise.loadById(exerciseId);
        if (exercise != null) {
            String[] input = getExerciseInput();
            exercise.setTitle(input[0]);
            exercise.setDescription(input[1]);
            if (exercise.save()) {
                System.out.println("Exercise edition saved successfully to the database.");
            }
        }
        runAgain( 2);
    }

    private static void deleteExercise() {
        System.out.println("\nDeleting Exercise from the database:");
        int exerciseId = Helper.getInputInt( "Type id of exercise to be deleted: ", Integer.MAX_VALUE);
        Exercise exercise = Exercise.loadById(exerciseId);
        if (exercise != null && exercise.delete()) {
            System.out.println("Exercise successfully deleted from the database.");
        }
        runAgain( 3);
    }

    private static String[] getExerciseInput() {
        System.out.print("Type exercise name: ");
        Helper.sc.nextLine();
        String name = Helper.sc.nextLine();
        System.out.print("Type exercise description: ");
        String email = Helper.sc.nextLine();

        String[] inputs = new String[2];
        inputs[0] = name;
        inputs[1] = email;
        return inputs;
    }

    private static void runAgain(int choice) {
        int again = Helper.getInputInt( Helper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        addExercise();
                        break;
                    case 2:
                        editExercise();
                        break;
                    case 3:
                        deleteExercise();
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

