package pl.zajacp.model;

import pl.zajacp.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise(String title, String description) {
        this.id = 0;
        this.title = title;
        this.description = description;
    }

    public Exercise() {
        this.id = 0;
        this.title = null;
        this.description = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void save() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (this.id == 0) {
                String sql = "INSERT INTO Exercises(title, description) VALUES (?,?)";
                PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                psmt.setString(1, this.title);
                psmt.setString(2, this.description);
                psmt.executeUpdate();
                ResultSet rs = psmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE Exercises SET title=?, description=? WHERE id=?";
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setString(1, this.title);
                psmt.setString(2, this.description);
                psmt.setInt(3, this.id);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public static Exercise loadById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Exercises where id=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = rs.getInt("id");
                loadedExercise.title = rs.getString("title");
                loadedExercise.description = rs.getString("description");
                return loadedExercise;
            }
        } catch (SQLException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
        System.out.println("Load failed: Record with given ID does not exist.");
        return null;
    }

    public static Exercise[] loadAll() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Exercise> users = new ArrayList<>();
            String sql = "SELECT * FROM Exercises";
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = rs.getInt("id");
                loadedExercise.title = rs.getString("title");
                loadedExercise.description = rs.getString("description");
                users.add(loadedExercise);
            }
            Exercise[] returnArray = new Exercise[users.size()];
            return users.toArray(returnArray);
        } catch (SQLException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
    }

    public void delete() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (this.id != 0) {
                String sql = "DELETE FROM Exercises WHERE id=?";
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setInt(1, this.id);
                pstm.executeUpdate();
                this.id = 0;
            }
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
