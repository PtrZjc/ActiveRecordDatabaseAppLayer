package pl.zajacp.model;

import org.mindrot.jbcrypt.BCrypt;
import pl.zajacp.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private Integer user_group_id;

    public User(String username, String email, String password, int user_group_id) {
        this.id = 0;
        this.username = username;
        this.setPassword(password);
        this.setEmail(email);
        this.user_group_id = user_group_id;
    }

    public User() {
        this.id = 0;
        this.username = null;
        this.email = null;
        this.password = null;
        this.user_group_id = null;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (checkEmail(email)) {
            this.email = email;
            return;
        }
        System.out.println("Email set failed: Incorrect input.");

    }

    public static boolean checkEmail(String email) {
        return Pattern.matches("[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)" +
                "*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}", email);

    }

    private void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String inputPassword, String hash) {
        return BCrypt.checkpw(inputPassword, hash);
    }

    public String getPassword() {
        return password;
    }

    public int getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(int user_group_id) {
        this.user_group_id = user_group_id;
    }

    public void save() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (this.id == 0) {
                String sql = "INSERT INTO Users(username, email, password, user_group_id) VALUES (?,?,?,?)";
                PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                psmt.setString(1, this.username);
                psmt.setString(2, this.email);
                psmt.setString(3, this.password);
                psmt.setInt(4, this.user_group_id);
                psmt.executeUpdate();
                ResultSet rs = psmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE Users SET username=?, email=?, password=?, user_group_id=? WHERE id=?";
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setString(1, this.username);
                psmt.setString(2, this.email);
                psmt.setString(3, this.password);
                psmt.setInt(4, this.user_group_id);
                psmt.setInt(5, this.id);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public static User loadById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users where id=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                User loadedUser = new User();
                loadedUser.id = rs.getInt("id");
                loadedUser.username = rs.getString("username");
                loadedUser.password = rs.getString("password");
                loadedUser.email = rs.getString("email");
                loadedUser.user_group_id = rs.getInt("user_group_id");
                return loadedUser;
            }
        } catch (SQLException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
        System.out.println("Load failed: Record with given ID does not exist.");
        return null;
    }

    public static User[] loadAll() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<User> users = new ArrayList<>();
            String sql = "SELECT * FROM Users";
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User loadedUser = new User();
                loadedUser.id = rs.getInt("id");
                loadedUser.username = rs.getString("username");
                loadedUser.password = rs.getString("password");
                loadedUser.email = rs.getString("email");
                loadedUser.user_group_id = rs.getInt("user_group_id");
                users.add(loadedUser);
            }
            User[] returnArray = new User[users.size()];
            return users.toArray(returnArray);
        } catch (SQLException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
    }

    public void delete() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (this.id != 0) {
                String sql = "DELETE FROM Users WHERE id=?";
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setInt(1, this.id);
                pstm.executeUpdate();
                this.id = 0;
            }
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    public static User[] loadAllByGroupId(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<User> users = new ArrayList<>();
            String sql = "SELECT * FROM Users WHERE user_group_id=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User loadedUser = new User();
                loadedUser.id = rs.getInt("id");
                loadedUser.username = rs.getString("username");
                loadedUser.password = rs.getString("password");
                loadedUser.email = rs.getString("email");
                loadedUser.user_group_id = rs.getInt("user_group_id");
                users.add(loadedUser);
            }
            User[] returnArray = new User[users.size()];
            return users.toArray(returnArray);
        } catch (SQLException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", user_group_id=" + user_group_id +
                '}';
    }
}