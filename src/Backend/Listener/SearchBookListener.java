/* This will help in Search/Filter table . ( Concerned with )*/

package Backend.Listener;

import Frontend.BookStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchBookListener implements KeyListener , ActionListener {

    BookStore bookStore;

    public SearchBookListener(BookStore bookStore) {
        this.bookStore = bookStore;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ApplySearch();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ApplySearch();
    }

    private void ApplySearch() {

        try {
            /* Getting Text */
            String searchData = bookStore.bookFilterPanel.tfSearchField.getText();

            /* Getting on which column Apply Filter */
            String onApplySearch=bookStore.bookFilterPanel.comboBoxFilter.getSelectedItem().toString();

            /* Setting for which column to apply */
            int columnOnTable = switch (onApplySearch) {
                case "Book Name" -> 1;
                case "Author Name" -> 3;
                case "Publication" -> 4;
                default -> 0;
            };

            DefaultTableModel tableModel = bookStore.bookTable.defaultTableModel;

            TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(tableModel);

            bookStore.bookTable.bookTable.setRowSorter(tableRowSorter);

            tableRowSorter.setRowFilter(RowFilter.regexFilter(searchData,columnOnTable));

        } catch (Exception e) {
            System.out.println("Error in searching : " + e + " Msg : " + e.getMessage());
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

}
