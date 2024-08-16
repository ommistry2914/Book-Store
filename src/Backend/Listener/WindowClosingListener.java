/* This will help when user closing frame and not saved data yet! */

package Backend.Listener;

import Backend.DataBaseConnection.CreateConnection;
import Frontend.BookStore;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class WindowClosingListener {

    private static BookStore bookStore;

    public static void ValidateWindowClosing(BookStore bookStore) {

        WindowClosingListener.bookStore=bookStore;

            int userChoice = JOptionPane.showConfirmDialog(bookStore, "Are you sure to want to Exit?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            /* userChoice :
            * 0 -> Yes
            * 1 -> No
            * other -> cancel
            * */

             if (userChoice==0) {

                 /* Close connection With DataBase */
                 try {
                     /* Fetching all Statement and Result set to close !! */
                     ArrayList<PreparedStatement> arrayList=BookActionListener.giveStatementForClosing();

                     arrayList.forEach(preparedStatement -> {
                         try {
                             preparedStatement.close();
                         } catch (Exception e) {
                             System.out.println("=> Error when closing " + e);
                         }
                     });

                     /* Closing connection with Database */
                     Connection connection = CreateConnection.getConnection();
                     connection.close();

                 } catch (Exception e) {
                     System.out.println("Error at closing Connection : " + e);
                 }

                 System.exit(0);
             }

             else {
                 bookStore.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
             }
    }

}
