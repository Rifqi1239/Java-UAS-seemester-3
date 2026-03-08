import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/penyimpanan";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            // Pastikan driver MySQL ada
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            if (conn == null) {
                throw new SQLException("Koneksi gagal: conn == null");
            }

            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL tidak ditemukan! Tambahkan mysql-connector-java.jar ke project Anda.");
            e.printStackTrace();
            return null;

        } catch (SQLException e) {
            System.err.println("Gagal konek ke database!");
            e.printStackTrace();
            return null;
        }
    }
}
