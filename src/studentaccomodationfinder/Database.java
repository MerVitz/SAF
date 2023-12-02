package studentaccomodationfinder;

import java.sql.*;

public class Database {

    public static Connection connect() {
        String url = "jdbc:mysql://localhost:3306/saf";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

public static void addNewUser(String username, String email, String password) throws SQLException {
    String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, username);
        pstmt.setString(2, email);
        pstmt.setString(3, password); // In real-world applications, you should hash the password

        int affectedRows = pstmt.executeUpdate();
        
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Rethrow the exception to be handled by the caller
    }
}


    public static boolean authenticateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // In a real application, use hashed passwords

            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // User found
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception for debugging
        }
        return false; // User not found or an error occurred
    }

    
}
   
