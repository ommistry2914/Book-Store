/* This Class Wake up main frontend page and Backend . */

import Frontend.BookStore;

import javax.swing.*;

public class HomePage {

    public static void main(String[] args) {

        BookStore bs = new BookStore("Book Store");

        bs.setVisible(true);
        bs.setLocation(60, 100);
        bs.setSize(1500, 700);
        bs.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
