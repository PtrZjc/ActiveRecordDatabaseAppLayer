package pl.zajacp;

import pl.zajacp.db.DatabaseConnection;
import pl.zajacp.model.Exercise;
import pl.zajacp.model.Solution;
import pl.zajacp.model.User;
import pl.zajacp.model.UserGroup;

import java.sql.*;
import java.time.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Solution[] sol = Solution.loadAllByUserId(6);
        Arrays.stream(sol).forEach(x -> System.out.println(x));
        Arrays.stream(sol).forEach(x -> x.save());
        sol = Solution.loadAllByUserId(6);
        Arrays.stream(sol).forEach(x -> System.out.println(x));


//        Solution loaded = Solution.loadById(15);
//        System.out.println(loaded);
//        loaded.setDescription("blablabla");
//        loaded.save();
    }


    static void tryConn() {
        try (Connection cnt = DatabaseConnection.getConnection()) {

            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Users;");
            while (rs.next()) {
                String username = rs.getString("username");
                int id = rs.getInt("id");
                System.out.println(id + ": " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void checkUserGroup() {
        UserGroup grupa = new UserGroup();
        grupa.setName("niebieskie ludziki");
        grupa.save();

        grupa = new UserGroup("zielone ludziki");
        grupa.save();

        UserGroup[] test = UserGroup.loadAll();
        System.out.println(Arrays.toString(test));
    }

    static void checkUser() {
        User user = new User("dsad", "gfdfds@ga.pl", "fdsf", 3);
        user.save();
        user = new User("goddy", "pdsts@op.pl", "cokolwiek", 2);
        user.save();
        User[] users = User.loadAll();
        System.out.println(Arrays.toString(users));
        users = User.loadAllByGroupId(3);
        System.out.println(Arrays.toString(users));

    }

    static void checkExercise() {
        Exercise user = new Exercise("zadanie", "zrob to i tamto");
        user.save();
        user = new Exercise("zadanie drugie", "zrob t oi siamto");
        user.save();
        Exercise[] users = Exercise.loadAll();
        System.out.println(Arrays.toString(users));
        users[1].delete();
        System.out.println(Arrays.toString(users));
        users = Exercise.loadAll();
        System.out.println(Arrays.toString(users));
    }

}


//metody do implementacji
//save()
//update()
//delete()

//statyczne:
//loadAll()
//loadById(id)
//deleteAll(
