package utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtils {

    private static DatabaseUtils instance;
    private static Connection connection;
    public ResultSet resultSet = null;
    public Statement statement = null;

    private DatabaseUtils(String userName, String password, String url) throws Exception {
        try {
            // Register the MySQL driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            // Establish the connection
            connection = DriverManager.getConnection(url, userName.trim(), password.trim());
            connection.setAutoCommit(true);
            System.out.println("Successfully Connected to MySQL...");

            // Create a statement
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseUtils getInstance(String userName, String password, String url) {
        try {
            if (instance == null) {
                instance = new DatabaseUtils(userName, password, url);
            } else if (instance.getConnection().isClosed()) {
                instance = new DatabaseUtils(userName, password, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static DatabaseUtils getInstance() {
        if (instance == null) {
            return null;
        } else {
            return instance;
        }
    }

    public String queryTable(String query,String Cloumnname) {
        String queryOutput = null;
        try {
            statement = instance.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery(query);
       
            while (rs.next()) {
            
                 queryOutput = rs.getString(Cloumnname);
           
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryOutput;
    }

    public static String querymysql() {
    	String result = null ;
        try {
            String userName = "root";
            String password = "";
            String url = "jdbc:mysql://localhost:3306/cocare";
            
            DatabaseUtils dbUtils = DatabaseUtils.getInstance(userName, password, url);
             result = dbUtils.queryTable("SELECT * FROM `auth_identities` where `type`='forgot_password_link' ORDER BY id DESC LIMIT 1;","secret");
            System.out.println("Query Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

