package utility;


import java.sql.DriverManager;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class DatabaseUtils2 {

    private static DatabaseUtils2 instance;
    private static Connection connection;
    private static String userName;
    private static String password;
    private static String url;
    private static String completeurl;


  

    // Private constructor for DatabaseUtils2
    private DatabaseUtils2() throws Exception {
    	 // Read the properties from the config file
    	 url =  EnvHelper.getValue("dburl");
    	 completeurl=getConnectionString();
       
        userName = EnvHelper.getValue("dbusername");
        password = EnvHelper.getValue("dbpassword");
        
        // Register the MySQL driver
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        // Establish the connection
        
        connection = DriverManager.getConnection(completeurl, userName.trim(), password.trim());
        connection.setAutoCommit(true);
        System.out.println("Successfully Connected to MySQL...");
    }

    

    // Singleton instance retrieval
    public static DatabaseUtils2 getInstance() throws Exception {
        if (instance == null) {
            synchronized (DatabaseUtils2.class) {
                if (instance == null) {
                    instance = new DatabaseUtils2();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Method to query the table and retrieve a column value
    public String queryTable(String query, String columnName) {
        String queryOutput = null;
        try (Statement statement = instance.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                queryOutput = rs.getString(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryOutput;
    }

    // Method to update the database column (password_expires_at)
    public static String updateDBcolumn(String columnName,String useremail,LocalDateTime pastDate) {
        String result = null;
        try {
            // Get DatabaseUtils2 instance
            DatabaseUtils2 dbUtils = DatabaseUtils2.getInstance();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = pastDate.format(formatter);

            // Create the UPDATE SQL query to set 'password_expires_at' to the past date
           String updateQuery = "UPDATE auth_identities SET " + columnName + " = ? , islocked = 0 WHERE auth_identities.secret = ? AND auth_identities.type = 'email_password'";

            // Execute the update query using prepared statement
            int rowsAffected = dbUtils.updateColumn(updateQuery, formattedDate, useremail);

            if (rowsAffected > 0) {
                result = "Successfully updated the the column.";
            } else {
                result = "No rows were updated.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "Error updating the database: " + e.getMessage();
        }
        return result;
    }
    
    
    public static String updateDBforcereset(String columnName,String columnvalue ,String useremail) {
        String result = null;
        try {
            // Get DatabaseUtils2 instance
            DatabaseUtils2 dbUtils = DatabaseUtils2.getInstance();
            // Create the UPDATE SQL query to set 'password_expires_at' to the past date
           String updateQuery = "UPDATE auth_identities SET " + columnName + " = ? , islocked = 0 WHERE auth_identities.secret = ? AND auth_identities.type = 'email_password'";

            // Execute the update query using prepared statement
            int rowsAffected = dbUtils.updateColumn(updateQuery, columnvalue, useremail);

            if (rowsAffected > 0) {
                result = "Successfully updated the the column.";
            } else {
                result = "No rows were updated.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "Error updating the database: " + e.getMessage();
        }
        return result;
    }
    
    
    public static String dbActivateAccount(String useremail) {
        String result = null;
        try {
            // Get DatabaseUtils2 instance
            DatabaseUtils2 dbUtils = DatabaseUtils2.getInstance();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime pastDate = currentDate.plus(5, ChronoUnit.DAYS);
            String lastuseddate=currentDate.format(formatter);
            String passwordexpired=pastDate.format(formatter);
            		
            // Create the UPDATE SQL query to set 'password_expires_at' to the past date
           String updateQuery = "UPDATE auth_identities SET force_reset = 0 , islocked = 0 , password_expires_at= ? ,last_used_at= ? WHERE auth_identities.secret = ? AND auth_identities.type = 'email_password'";

            // Execute the update query using prepared statement
            int rowsAffected = dbUtils.updateColumn(updateQuery, passwordexpired,lastuseddate,useremail);

            if (rowsAffected > 0) {
                result = "Successfully updated the the column.";
            } else {
                result = "No rows were updated.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "Error updating the database: " + e.getMessage();
        }
        return result;
    }

    // Method to get the connection string from the config file
    public String getConnectionString() {
        return "jdbc:mysql://" + url;
    }

    // Method to update a column in a table (supports parameterized queries)
    public int updateColumn(String query, String... params) throws Exception {
        int rowsAffected = 0;
        try (Connection conn = DriverManager.getConnection(getConnectionString(), userName, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters for the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            // Execute the update
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error updating the database: " + e.getMessage(), e);
        }
        return rowsAffected;
    }
}
