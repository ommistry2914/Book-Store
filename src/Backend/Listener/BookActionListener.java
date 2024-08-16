/* This Class is for Action Listener for Buttons
*
*  Operation in this  listener :
*                              1) Add
*                              2) Delete
*                              3) Update
*                              4) Cancel
*                              5) Clear Field
*                              6) Fetch all Previous store data
*                              7) Fetch BookCover
*
*  This will provide or complete job by calling Controller "PerformOperationOnBookData".
*  */


package Backend.Listener;

import Backend.Controller.PerformOperationOnBookData;
import Backend.DataBaseConnection.CreateConnection;
import Backend.Modal.BookDataClass;
import Frontend.BookStore;
import Frontend.Helpers.Toast;
import com.raven.datechooser.DateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookActionListener implements ActionListener {

    /* main frame */
    public BookStore bookStore;
    /* Operation On Book (as a Controller)*/
    private final PerformOperationOnBookData performOperationOnBookData;

    /* ArrayList of Book Data (Helpful in Update and Delete when selected row data will be showed up in text field) */
    private static ArrayList<BookDataClass> bookDataClassArrayList;

    /* ArrayList for BookId (Helps to check ID is not assign already) */
    public static ArrayList<Integer> idOfBooks;

    /* Connection of Book */
    private static Connection connectionOfDataBase=null;

    /*  <----- Prepared Statements ------> */
    private static PreparedStatement psFetchAllData,psAddOneBookData,psDeleteOneBookData,psUpdateOneBookData;

    /* Actual data of Book Data (Modal) */
    private int bookId,bookPrice=200,bookQuantity=1,totalCost=bookPrice*bookQuantity;
    private String bookName,bookSubject,authorName,dateOfPublication,publication,bookCoverPath="src\\assets\\byDefaultCover.jpg";

    static{

        bookDataClassArrayList=new ArrayList<>();
        idOfBooks=new ArrayList<>();

        try {
            connectionOfDataBase= CreateConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Error  at Connecting to Database from frontend : " + e.getMessage());
        }


        /* <- Fetching  data  --> */
        try {
            psFetchAllData = connectionOfDataBase.prepareStatement("SELECT * FROM bookdata");
        } catch (Exception e) {
            System.out.println("Error at Preparing Statement for fetching data : " + e.getMessage());
        }

        /* <- Adding Data --> */
        try {
            psAddOneBookData = connectionOfDataBase.prepareStatement("INSERT INTO bookdata VALUES(?,?,?,?,?,?,?,?,?,?) ;");
        } catch (Exception e) {
            System.out.println("Error at Preparing Statement for Adding data : " + e.getMessage());
        }

        /* <- Updating Data --> */
        try {
            psUpdateOneBookData = connectionOfDataBase.prepareStatement("UPDATE bookdata SET bookName=?,bookSubject=?,authorName=?,publication=?,dateOfPublication=?,bookPrice=?,bookQuantity=?,totalCost=?,bookCoverPath=? WHERE bookId=?;");
        } catch (Exception e) {
            System.out.println("Error at Preparing Statement for Updating data : " + e.getMessage());
        }

        /* <-- Delete Data --> */
        try {
            psDeleteOneBookData = connectionOfDataBase.prepareStatement("DELETE FROM BookData WHERE bookId=? ;");
        } catch (Exception e) {
            System.out.println("Error at Preparing Statement for Deleting data : " + e.getMessage());
        }

    }
    public BookActionListener(BookStore bookStore) {
        this.bookStore = bookStore;
        performOperationOnBookData =new PerformOperationOnBookData();
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        String operationHappen=event.getActionCommand();

        try {

            switch (operationHappen) {

                case "Add" -> doAddOperation();
                case "Update" -> doUpdateOperation();
                case "Delete" -> doDeleteOperation();
                case "Cancel" -> doCancelOperation();
                case "Cover" -> browseCover();
            }
        } catch (Exception e) {
            System.out.println("error at calling functions  : " + e);
        }

    }

    /* User defined methods */

    public void FetchAllBooks() throws SQLException {
        /* This method call from BookStore constructor only for one time */
        ResultSet resultSet=null;
                
        try {

            /* Execute Fetch All Prepared Statement */
            resultSet=psFetchAllData.executeQuery();

            if (resultSet == null) {
                return;
            }

            /* 1) Iterate resultSet
            *  2) Create New Object of BookDataClass
            *  3) Adding it to ArrayList
            *  4) Adding it to JTable */

            BookDataClass fetchedBookDataClass=null;

            while (resultSet.next()) {

                /* Step 1 :  Fetching Data from Result set */
                bookId = resultSet.getInt(1);
                idOfBooks.add(bookId); /* Adding book ID in array list , helpful in validation */

                bookName = resultSet.getString(2);
                bookSubject = resultSet.getString(3);
                authorName =resultSet.getString(4);
                publication = resultSet.getString(5);
                Date date = resultSet.getDate(6);
                dateOfPublication=date.toString();
                bookPrice = resultSet.getInt(7);
                bookQuantity = resultSet.getInt(8);
                totalCost =resultSet.getInt(9);
                bookCoverPath = resultSet.getString(10);

                /* Step 2 :  Creating an Object of BookDataClass */
                fetchedBookDataClass = new BookDataClass(bookId,bookName,bookSubject,authorName,publication,dateOfPublication,bookPrice,bookQuantity,totalCost,bookCoverPath);

                /* Step 3 : Adding it to ArrayList */
                bookDataClassArrayList.add(fetchedBookDataClass);

                /* Step 4 : Adding it to JTable Row */

                /* Referencing Table Modal */
                DefaultTableModel tableModel = bookStore.bookTable.defaultTableModel;

                /* Setting Image to row */
                JLabel imgLabelInRow=new JLabel();
                ImageIcon bookCoverIcon = new ImageIcon(bookCoverPath);
                Image img = bookCoverIcon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
                imgLabelInRow.setIcon(new ImageIcon(img));

                /* Making One Row */
                Object[] dataOfRow = {bookId, bookName, bookSubject, authorName, publication, dateOfPublication, bookPrice, bookQuantity, totalCost, imgLabelInRow};

                /* Adding One Row */
                tableModel.addRow(dataOfRow);
            }
        } catch (Exception e) {
            System.out.println("Error  at Fetching data Listener : " + e.getMessage());
        }//catch close
        finally {
            if (resultSet!=null) {
                resultSet.close();
            }
        }//finally close
    
    }//Method close

    /* <------------ Adding One data ------------>*/

    private void doAddOperation() throws SQLException {

        /* Checking Validation  */
        boolean shouldGoFurther=checkValidation(0);

        if (!shouldGoFurther) {
            return;
        }

        /* <-- Adding into Database -->*/

        int RowAffected=0;

        try {
            psAddOneBookData.setInt(1,bookId);
            psAddOneBookData.setString(2,bookName);
            psAddOneBookData.setString(3,bookSubject);
            psAddOneBookData.setString(4,authorName);
            psAddOneBookData.setString(5,publication);

            java.sql.Date dateSelected=java.sql.Date.valueOf(dateOfPublication); /* Converting String into Sql Date */
            psAddOneBookData.setDate(6,dateSelected);

            psAddOneBookData.setInt(7,bookPrice);
            psAddOneBookData.setInt(8,bookQuantity);
            psAddOneBookData.setInt(9,totalCost);
            psAddOneBookData.setString(10,bookCoverPath);

            RowAffected=psAddOneBookData.executeUpdate();

            if (RowAffected<=0) {
                return;
            }

        } catch (Exception e) {
            System.out.println(e);

            if (RowAffected<=0) {
                return;
            }
        }//catch close


        /* Data of Book (Modal) */
        BookDataClass bookDataClass = new BookDataClass(bookId,bookName,bookSubject,authorName,publication,dateOfPublication,bookPrice,bookQuantity,totalCost,bookCoverPath);

        /* Setting Image to row */
        JLabel imgLabelInRow=new JLabel();
        ImageIcon bookCoverIcon = new ImageIcon(bookCoverPath);
        Image img = bookCoverIcon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        imgLabelInRow.setIcon(new ImageIcon(img));

        /* I have to Add This at 3 Place
         *
         * 1) In DaTaBase
         * 2) In ArrayList
         * 3) Adding data in Table Row.
         * */

        try {

            if (bookDataClassArrayList == null) {
                bookDataClassArrayList=new ArrayList<>();
            }

            /* <--- Adding object in arrayList ---> */
            bookDataClassArrayList.add(bookDataClass);
            /* Adding book ID in array list , helpful in validation */
            idOfBooks.add(bookId);

            /* <--- Adding it to JTable Row ---> */
            /* Referencing Table Modal */
            DefaultTableModel tableModel=bookStore.bookTable.defaultTableModel;
            Object[] dataOfRow = {bookId,bookName,bookSubject,authorName,publication,dateOfPublication,bookPrice,bookQuantity,totalCost,imgLabelInRow};
            tableModel.addRow(dataOfRow);

            clearInputFields();

            ShowToastOnOperation("Data added Successfully  !!");

        } catch (Exception e) {
            System.out.println("Error  at listener Add : " + e.getMessage());
        }

    }

    /* <------------ Updating One data ------------>*/

    private void doUpdateOperation() throws SQLException {

        int rowSelected=RowSelectionListener.selectedRow;
        int rowAffected=0;

        try {

            /* Check Whether Row is selected or Not */
            if (rowSelected < 0) {
                JOptionPane.showMessageDialog(null, "No row is selected!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            /* Taking confirmation */
            int input = JOptionPane.showConfirmDialog(null, "Are you sure to update?", "Update", JOptionPane.YES_NO_OPTION);
            // input : 0=yes, 1=no

            if (input == 1 || input == -1) {
                /* DeSelect row */
                bookStore.bookTable.bookTable.getSelectionModel().clearSelection();

                clearInputFields();

                /* Reset ID Field which  was changed when Row selected */
                bookStore.addBookPanel.tfBookID.setEditable(true);
                Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                bookStore.addBookPanel.tfBookID.setCursor(cursor);

                return;
            }
            /* Checking Validation  */
            boolean shouldGoFurther = checkValidation(1);

            if (!shouldGoFurther) {
                return;
            }

            /* <----------  Updating from Database  ----------> */

            /* <-- Setting Values --> */

            psUpdateOneBookData.setString(1,bookName);
            psUpdateOneBookData.setString(2,bookSubject);
            psUpdateOneBookData.setString(3,authorName);
            psUpdateOneBookData.setString(4,publication);

            java.sql.Date dateSelected=java.sql.Date.valueOf(dateOfPublication); /* Converting String into Sql Date */
            psUpdateOneBookData.setDate(5,dateSelected);

            psUpdateOneBookData.setInt(6,bookPrice);
            psUpdateOneBookData.setInt(7,bookQuantity);
            psUpdateOneBookData.setInt(8,totalCost);
            psUpdateOneBookData.setString(9,bookCoverPath);

            psUpdateOneBookData.setInt(10,bookId); /*Where clause */

            rowAffected=psUpdateOneBookData.executeUpdate();

            if (rowAffected <= 0) {
                return;
            }

            /*  <---------- if ( Selected & confirmed ) then Update it to JTable  ---------- */
            DefaultTableModel updateTableModel = bookStore.bookTable.defaultTableModel;

            updateTableModel.setValueAt(bookId, rowSelected, 0);
            updateTableModel.setValueAt(bookName, rowSelected, 1);
            updateTableModel.setValueAt(bookSubject, rowSelected, 2);
            updateTableModel.setValueAt(authorName, rowSelected, 3);
            updateTableModel.setValueAt(publication, rowSelected, 4);
            updateTableModel.setValueAt(dateOfPublication, rowSelected, 5);
            updateTableModel.setValueAt(bookPrice, rowSelected, 6);
            updateTableModel.setValueAt(bookQuantity, rowSelected, 7);
            updateTableModel.setValueAt(totalCost, rowSelected, 8);

            /* Setting Image to row */
            JLabel imgLabelInRow = new JLabel();
            ImageIcon bookCoverIcon = new ImageIcon(bookCoverPath);
            Image img = bookCoverIcon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
            imgLabelInRow.setIcon(new ImageIcon(img));

            updateTableModel.setValueAt(imgLabelInRow, rowSelected, 9);

            /*  <---------- Also from ArrayList ----------> */

            BookDataClass updatedBookDataClass = new BookDataClass(bookId, bookName, bookSubject, authorName, publication, dateOfPublication, bookPrice, bookQuantity, totalCost, bookCoverPath);
            bookDataClassArrayList.set(rowSelected, updatedBookDataClass);

            clearInputFields();

            /* DeSelect row */
            bookStore.bookTable.bookTable.getSelectionModel().clearSelection();

            /* Reset ID Field which  was changed when Row selected */
            bookStore.addBookPanel.tfBookID.setEditable(true);
            Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
            bookStore.addBookPanel.tfBookID.setCursor(cursor);

            ShowToastOnOperation("Data updated Successfully  !!");

        } catch (Exception e) {
            System.out.println("Error at updating database : - " + e);
        }
    }

    /* <------------ Deleting  One data ------------>*/

    private void doDeleteOperation() throws SQLException {

        int rowSelected=RowSelectionListener.selectedRow;
        int rowAffected=0;

        try {

            /* Check Whether Row is selected or Not */
            if (rowSelected < 0) {
                JOptionPane.showMessageDialog(null, "No row is selected!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            /* Taking confirmation */
            int input = JOptionPane.showConfirmDialog(null, "Are you sure to delete?", "Delete", JOptionPane.YES_NO_OPTION);
            // input : 0=yes, 1=no

            if (input == 1 || input == -1) {
                /* DeSelect row */
                bookStore.bookTable.bookTable.getSelectionModel().clearSelection();

                clearInputFields();

                /* Reset ID Field which  was changed when Row selected */
                bookStore.addBookPanel.tfBookID.setEditable(true);
                Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                bookStore.addBookPanel.tfBookID.setCursor(cursor);

                return;
            }

            /* <--- Delete  From DataBase ---->  */

            /* Getting Id */
            int deleteId=bookDataClassArrayList.get(rowSelected).getBookId();

            psDeleteOneBookData.setInt(1,deleteId);

            rowAffected=psDeleteOneBookData.executeUpdate();

            if (rowAffected<=0) {
                return;
            }


            /* if ( Selected & confirmed ) then Remove it from JTable*/
            bookStore.bookTable.defaultTableModel.removeRow(rowSelected);

            /* Also from ArrayList */
            bookDataClassArrayList.remove(rowSelected);

            clearInputFields();

            /* DeSelect row */
            bookStore.bookTable.bookTable.getSelectionModel().clearSelection();

            /* Reset ID Field which  was changed when Row selected */
            bookStore.addBookPanel.tfBookID.setEditable(true);
            Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
            bookStore.addBookPanel.tfBookID.setCursor(cursor);

            ShowToastOnOperation("Data deleted Successfully  !!");

        } catch (Exception e) {
            System.out.println("Error at deleting data : " + e);
        }
    }

    private void doCancelOperation() {
        /* Eventually it is clear field Operation but have to add confirmation */

        try {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure to cancel?", "Cancel", JOptionPane.YES_NO_OPTION);
            // input : 0=yes, 1=no

            if (input == 0 || input == -1) {
                /* DeSelect row */
                bookStore.bookTable.bookTable.getSelectionModel().clearSelection();

                clearInputFields();

                /* Reset ID Field which  was changed when Row selected */
                bookStore.addBookPanel.tfBookID.setEditable(true);
                Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                bookStore.addBookPanel.tfBookID.setCursor(cursor);

                if (input==0) {
                    ShowToastOnOperation("Operation canceled successfully!!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error  at cancel operation : " + e.getMessage());
        }

    }

    private void browseCover() {

        /* Taking path */
        String pathOfBookCover= performOperationOnBookData.fetchBookCover();

        /* Updating in frontend so Take value when adding */
        bookStore.addBookPanel.bookCover.bookCoverPath=pathOfBookCover;

        /* Taking reference from Frame*/
        JLabel bookCoverImage=bookStore.addBookPanel.bookCover.bookCoverImage;

        /* Setting Cover on Frame */
        ImageIcon bookCoverIcon = new ImageIcon(pathOfBookCover);
        Image img = bookCoverIcon.getImage().getScaledInstance(bookCoverImage.getWidth(), bookCoverImage.getHeight(), Image.SCALE_SMOOTH);
        bookCoverImage.setIcon(new ImageIcon(img));

        if (!pathOfBookCover.equals("src\\assets\\byDefaultCover.jpg")) {
            ShowToastOnOperation("Image Browse Successfully!!");
        }

    }

    private void clearInputFields() {

        bookStore.addBookPanel.tfBookID.setText("");
        bookStore.addBookPanel.tfBookName.setText("");
        bookStore.addBookPanel.tfBookSubject.setText("");
        bookStore.addBookPanel.tfAuthorName.setText("");
        bookStore.addBookPanel.tfPublication.setText("");

        DateChooser dateChooser = bookStore.addBookPanel.dateChooser;
        dateChooser.setDateFormat("yyyy-MM-dd");
        dateChooser.setTextRefernce(bookStore.addBookPanel.tfDatePublication);
        dateChooser.setSelectedDate(new Date());

        bookStore.addBookPanel.spBookPrice.setValue(200);
        bookStore.addBookPanel.spBookQuantity.setValue(1);
        bookStore.addBookPanel.tfTotalCost.setText("200");
        bookStore.addBookPanel.bookCover.bookCoverPath="src\\assets\\byDefaultCover.jpg";


        /* Taking reference from Frame*/
        JLabel bookCoverImage=bookStore.addBookPanel.bookCover.bookCoverImage;

        /* Setting Cover on Frame */
        ImageIcon bookCoverIcon = new ImageIcon("src\\assets\\byDefaultCover.jpg");
        Image img = bookCoverIcon.getImage().getScaledInstance(bookCoverImage.getWidth(), bookCoverImage.getHeight(), Image.SCALE_SMOOTH);
        bookCoverImage.setIcon(new ImageIcon(img));

    }

    public boolean checkValidation(int forOperation) {

        /* forOperation :
        * 0 : Add
        * 1 : Update
        *  For Update 'ID' validation will be omitted.
        * */

        try {

            /* Fetching All data from frontend */

            bookId=Integer.parseInt(bookStore.addBookPanel.tfBookID.getText());
            bookName=bookStore.addBookPanel.tfBookName.getText();
            bookSubject=bookStore.addBookPanel.tfBookSubject.getText();
            authorName=bookStore.addBookPanel.tfAuthorName.getText();
            publication=bookStore.addBookPanel.tfPublication.getText();
            dateOfPublication=bookStore.addBookPanel.tfDatePublication.getText();
            bookPrice=(Integer) bookStore.addBookPanel.spBookPrice.getValue();
            bookQuantity=(Integer) bookStore.addBookPanel.spBookQuantity.getValue();
            totalCost=Integer.parseInt(bookStore.addBookPanel.tfTotalCost.getText());
            bookCoverPath=bookStore.addBookPanel.bookCover.bookCoverPath;

            if (forOperation==0) { /* Only for 'Add' */

                /* Checking For ID is duplicate or not */
                if (idOfBooks.contains(bookId)) {
                    JOptionPane.showMessageDialog(null,"Id : '" + bookId +"' is already assigned!!","Error",JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            }

            /* Checking Date is not greater than current date. */

            /* Fetching Current date*/
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String todayDate = formatter.format(date);

            Date selectedDate=formatter.parse(dateOfPublication);
            Date currentDate=formatter.parse(todayDate);

            int shouldAllowed = selectedDate.compareTo(currentDate);

            if (shouldAllowed > 0) {
                JOptionPane.showMessageDialog(null,"Publication date can't be in future","Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            /* Checking if any contain null or empty */
            if (bookName.isEmpty() || bookSubject.isEmpty() || authorName.isEmpty() || publication.isEmpty() || dateOfPublication.isEmpty() || bookCoverPath.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Maybe Some Inputs are missing!!","Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            /* For String (No digit) : Other fields */
            String regExForString = "^[ A-Za-z]+$";
            Pattern pattern=Pattern.compile(regExForString);
            Matcher matcher;

            String[] listForRegEx={bookName,authorName,publication};

            for (String forRegEx : listForRegEx) {

                matcher=pattern.matcher(forRegEx);

                if (!matcher.matches()) {
                    JOptionPane.showMessageDialog(null,"Can't have digit in String input","Error",JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Some Inputs are not Proper!!","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println("Error in Regex : " + e + " Msg : " + e.getMessage());
            return false;
        }
        return true;
    }

    public static ArrayList<BookDataClass> giveDataWhenRowSelected() {
        return bookDataClassArrayList;
    }

    public static ArrayList<PreparedStatement> giveStatementForClosing(){
        ArrayList<PreparedStatement> arrayList=new ArrayList<>();

        arrayList.add(psFetchAllData);
        arrayList.add(psAddOneBookData);
        arrayList.add(psDeleteOneBookData);
        arrayList.add(psUpdateOneBookData);

        return arrayList;
    }

    private void ShowToastOnOperation(String operationDone) {
        try {
            Toast toast=new Toast(operationDone,610,550);
            toast.showtoast();
        } catch (Exception e) {
            System.out.println("Error in showing Toast (Action Listener)!!");
        }

    }
} // class close
