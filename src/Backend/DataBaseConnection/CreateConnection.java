package Backend.DataBaseConnection;

import Backend.FileManagment.CredentialByFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

public class CreateConnection {

    private static Connection connectionToDataBase=null;

    public static Connection getConnection(){

        try {
            /* Fetching Credential */

          HashMap<String,String> credential= CredentialByFile.getCredential();

            /* Credential */
            String userName = credential.get("userName");
            String password = credential.get("password");

            /* Loading Driver Class*/
            Class.forName("com.mysql.cj.jdbc.Driver");

            /* Establishing Connection */
//            connectionToDataBase= DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", userName, password);
            connectionToDataBase = DriverManager.getConnection("jdbc:mysql://localhost:3306/book", userName, password);

        } catch (Exception e) {
            System.out.println("\n => Error at creating connection : " + e.getMessage());
        }

        return connectionToDataBase;
    }
}
