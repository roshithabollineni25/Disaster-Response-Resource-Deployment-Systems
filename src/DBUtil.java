import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    static String url = "jdbc:mysql://localhost:3306/disaster_db";
    static String user = "root";
    static String pass = "root";

    public static Connection getConnection() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,user,pass);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}