package pl.zajacp.model;

import pl.zajacp.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserGroup {

    private int id;
    private String name;

    public UserGroup(String name) {
        this.id = 0;
        this.name = name;
    }

    public UserGroup() {
        this.id = 0;
        this.name = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean save() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (this.id == 0) {
                String sql = "INSERT INTO User_groups(name) VALUES (?)";
                PreparedStatement psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                psmt.setString(1, this.name);
                psmt.executeUpdate();
                ResultSet rs = psmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE User_groups SET name=? WHERE id = ?";
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setString(1, this.name);
                psmt.setInt(2, this.id);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Save failed: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static UserGroup loadById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM User_groups where id=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                UserGroup loadedUserGroup = new UserGroup();
                loadedUserGroup.id = rs.getInt("id");
                loadedUserGroup.name = rs.getString("name");
                return loadedUserGroup;
            }
        } catch (SQLException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
        System.out.println("Load failed: Record with given ID does not exist.");
        return null;
    }

    public static UserGroup[] loadAll() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<UserGroup> userGroups = new ArrayList<>();
            String sql = "SELECT * FROM User_groups";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                UserGroup loadedUserGroup = new UserGroup();
                loadedUserGroup.id = rs.getInt("id");
                loadedUserGroup.name = rs.getString("name");
                userGroups.add(loadedUserGroup);
            }
            UserGroup[] returnArray = new UserGroup[userGroups.size()];
            return userGroups.toArray(returnArray);
        } catch (SQLException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
    }

    public boolean delete() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (this.id != 0) {
                String sql = "DELETE FROM User_groups WHERE id=?";
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setInt(1, this.id);
                pstm.executeUpdate();
                this.id = 0;

            }
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("UserGroup{")
                .append("id=")
                .append(id)
                .append(", name='")
                .append(name)
                .append('\'')
                .append('}')
                .toString();
    }
}
