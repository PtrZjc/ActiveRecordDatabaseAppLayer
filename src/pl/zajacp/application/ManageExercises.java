package pl.zajacp.application;

import pl.zajacp.application.helper.ManageHelper;
import pl.zajacp.model.Exercise;

import java.util.Arrays;
import java.util.Scanner;

public class ManageExercises {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        crudMenu(sc);
    }

    private static void crudMenu(Scanner sc) {
        int choice = ManageHelper.getInputInt(sc, ManageHelper.CRUD_SELECTION, 5);
        switch (choice) {
            case 1:
                addExercise(sc);
                break;
            case 2:
                editExercise(sc);
                break;
            case 3:
                deleteExercise(sc);
                break;
            case 4:
                try {
                    System.out.println("\n All exercises present in the database:");
                    Arrays.stream(Exercise.loadAll()).forEach(x -> System.out.println(x));
                } catch (NullPointerException e) {
                    System.out.println("\n There are no records in the database.");
                }
                System.out.println();
                crudMenu(sc);
                break;
        }
    }

    private static void addExercise(Scanner sc) {
        System.out.println("\nAdding new exercise to the database:");

        String[] input = getExerciseInput(sc);
        Exercise exercise = new Exercise();
        exercise.setTitle(input[0]);
        exercise.setDescription(input[1]);

        System.out.println();
        if (exercise.save()) {
            System.out.println("Exercise added successfully to the database.");
        }
        runAgain(sc, 1);
    }

    private static void editExercise(Scanner sc) {
        System.out.println("\nEditing existing Exercise in the database:");
        int exerciseId = ManageHelper.getInputInt(sc, "Type id of exercise to be edited: ", Integer.MAX_VALUE);
        Exercise exercise = Exercise.loadById(exerciseId);
        if (exercise != null) {
            String[] input = getExerciseInput(sc);
            exercise.setTitle(input[0]);
            exercise.setDescription(input[1]);
            if (exercise.save()) {
                System.out.println("Exercise edition saved successfully to the database.");
            }
        }
        runAgain(sc, 2);
    }

    private static void deleteExercise(Scanner sc) {

        System.out.println("\nDeleting Exercise from the database:");
        int exerciseId = ManageHelper.getInputInt(sc, "Type id of exercise to be deleted: ", Integer.MAX_VALUE);
        Exercise exercise = Exercise.loadById(exerciseId);
        if (exercise != null && exercise.delete()) {
            System.out.println("Exercise successfully deleted from the database.");
        }
        runAgain(sc, 3);
    }

    private static String[] getExerciseInput(Scanner sc) {
        System.out.print("Type exercise name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Type exercise description: ");
        String email = sc.nextLine();

        String[] inputs = new String[2];
        inputs[0] = name;
        inputs[1] = email;
        return inputs;
    }


    private static void runAgain(Scanner sc, int choice) {
        int again = ManageHelper.getInputInt(sc, ManageHelper.ANOTHER_OPERATION, 2);
        switch (again) {
            case 1:
                switch (choice) {
                    case 1:
                        addExercise(sc);
                        break;
                    case 2:
                        editExercise(sc);
                        break;
                    case 3:
                        deleteExercise(sc);
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

