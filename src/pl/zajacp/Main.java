package pl.zajacp;

import pl.zajacp.db.DatabaseConnection;
import pl.zajacp.model.Exercise;
import pl.zajacp.model.User;
import pl.zajacp.model.UserGroup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        voidCheckUser();

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

    static void voidCheckUser() {
        User user = new User("dsad", "gfdfds@ga.pl", "fdsf", 3);
        user.save();
        user = new User("goddy", "pdsts@op.pl", "cokolwiek", 2);
        user.save();
        User[] users = User.loadAll();
        System.out.println(Arrays.toString(users));
        users = User.loadAllByGroupId(3);
        System.out.println(Arrays.toString(users));

    }

    static void voidCheckExercise() {
        Exercise user = new Exercise("dsad", "gfdfds@ga.pl", "fdsf", 3);
        user.save();
        user = new Exercise("goddy", "pdsts@op.pl", "cokolwiek", 2);
        user.save();
        Exercise[] users = Exercise.loadAll();
        System.out.println(Arrays.toString(users));
        users = Exercise.loadAllByGroupId(3);
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
