package Frontend.BooksPanel.AddBookPanel;

import Backend.Listener.BookActionListener;
import Frontend.BookStore;

import javax.swing.*;
import java.awt.*;

/* It contains Operational Button */
public class OperationButtonPanel extends JPanel {

    /* Main frame */
    BookStore mainContainer;

    /*Action Listener clas*/
    BookActionListener bookActionListener;

    /* Component */
    public JButton btnAdd, btnCancel, btnDelete, btnUpdate;
    ImageIcon addIcon, updateIcon, cancelIcon, deleteIcon;

    OperationButtonPanel(BookStore mainContainer) {
        this.mainContainer = mainContainer;

        /* Intialize Listener */
        bookActionListener = new BookActionListener(mainContainer);

        /* Button 1 : Add */
        addIcon = new ImageIcon("src\\assets\\addIcon.png");
        btnAdd = new JButton("Add", addIcon);
        btnAdd.setFont(new Font("Arial Rounded MT", Font.PLAIN, 22));
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setBackground(new Color(0, 103, 184));
        btnAdd.addActionListener(bookActionListener);
        this.add(btnAdd);

        /* Button 2 : Update */
        updateIcon = new ImageIcon("src\\assets\\updateIcon.png");
        btnUpdate = new JButton("Update", updateIcon);
        btnUpdate.setFont(new Font("Arial Rounded MT", Font.PLAIN, 22));
        btnUpdate.setForeground(new Color(255, 255, 255));
        btnUpdate.setBackground(new Color(0, 103, 184));
        btnUpdate.addActionListener(bookActionListener);
        this.add(btnUpdate);

        /* Button 3 : Delete */
        deleteIcon = new ImageIcon("src\\assets\\deleteIcon.png");
        btnDelete = new JButton("Delete", deleteIcon);
        btnDelete.setFont(new Font("Arial Rounded MT", Font.PLAIN, 22));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setBackground(new Color(0, 103, 184));
        btnDelete.addActionListener(bookActionListener);
        this.add(btnDelete);

        /* Button 4 : Clear */
        cancelIcon = new ImageIcon("src\\assets\\cancelIcon.png");
        btnCancel = new JButton("Cancel", cancelIcon);
        btnCancel.setFont(new Font("Arial Rounded MT", Font.PLAIN, 22));
        btnCancel.setForeground(new Color(255, 255, 255));
        btnCancel.setBackground(new Color(0, 103, 184));
        btnCancel.addActionListener(bookActionListener);
        this.add(btnCancel);

    }


}
