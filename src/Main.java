import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] arg) {
        String SQL = "SELECT datname FROM pg_database;";

        try (Connection conn = DatabaseConnection.connectToRDS();

             PreparedStatement pst = conn.prepareStatement(SQL);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                //int id = rs.getInt("id");
                String datname = rs.getString("datname");
                System.out.println(datname);
            }
        } catch ( SQLException ex) {
            System.out.println(ex.getMessage());
        }

   }
}
