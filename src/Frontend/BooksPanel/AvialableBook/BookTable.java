package Frontend.BooksPanel.AvialableBook;

import Backend.Listener.RowSelectionListener;
import Frontend.BookStore;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class BookTable extends JPanel {

    /* Main container */
    BookStore mainContainer;

    /* Component */
    public DefaultTableModel defaultTableModel;
    public JTable bookTable;
    JScrollPane jspBookTable;

    /* Selection Listener */
    RowSelectionListener rowSelectionListener;

    /* Variables */
    String[] column = {"ID", "BOOK NAME", "BOOK SUBJECT", "AUTHOR NAME", "PUBLICATION", "DATE", "PRICE", "QUANTITY", "TOTAL COST", "COVER"};

    public BookTable(BookStore mainContainer) {
        this.mainContainer = mainContainer;

        /* Initialize listener */
        rowSelectionListener=new RowSelectionListener(mainContainer);

        {
            defaultTableModel = new DefaultTableModel(column, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;  //all cells are not editable!
                }
            };
        }

//        color of header : #4f81bd

        bookTable = new JTable(defaultTableModel){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component comp = super.prepareRenderer(renderer, row, column);
            Color alternateColor = new Color(202, 217, 224, 220);
            Color whiteColor = new Color(233, 240, 245, 255);
            if(!comp.getBackground().equals(getSelectionBackground())) {
                Color backGround = (row % 2 == 0 ? whiteColor :  alternateColor);
                comp.setBackground(backGround);
                comp.setFont(new java.awt.Font("Yu Gothic Medium", Font.BOLD, 16));
            }
            return comp;
        }
    };
        bookTable.setVisible(true);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); /* Select Only One Row at a time */
        bookTable.getSelectionModel().addListSelectionListener(rowSelectionListener);
        bookTable.setRowHeight(60);

        /* Setting table header */
        bookTable.getTableHeader().setForeground(new Color(255, 255, 255));
        bookTable.getTableHeader().setBackground(new Color(0, 103, 184));
        bookTable.getTableHeader().setAlignmentY(SwingConstants.CENTER);
        bookTable.getTableHeader().setFont(new java.awt.Font("Yu Gothic Medium", Font.BOLD, 16));

        /* Center data row*/
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        for(int columnNo=0;columnNo<column.length-1;columnNo++){
            bookTable.getColumnModel().getColumn(columnNo).setCellRenderer(centerRenderer );
        }

        /* Modifying for store the image in row*/
        bookTable.getColumn("COVER").setCellRenderer(new MyTableCellRender());
        TableColumn tableColumn=bookTable.getColumn("COVER");
        tableColumn.setMaxWidth(120);
        tableColumn.setMinWidth(120);

        jspBookTable = new JScrollPane(bookTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspBookTable.setBounds(0, 0, 1500, 400);

        this.add(jspBookTable);
        validate(); /* At loading Table visible */
    }

}

class MyTableCellRender implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        return (Component) value;
    }
}
