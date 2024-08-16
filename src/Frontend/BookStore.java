package Frontend;

import Backend.DataBaseConnection.CreateConnection;
import Backend.Listener.BookActionListener;
import Backend.Listener.WindowClosingListener;
import Frontend.BooksPanel.AddBookPanel.AddBookPanel;
import Frontend.BooksPanel.AvialableBook.BookTable;
import Frontend.BooksPanel.FilterBookPanel.BookFilterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

/* It is a Main Frame */
public class BookStore extends JFrame {

    /* Component */
    public JPanel mainPanel;
    public JScrollPane mainScrollPane;
    public JLabel mainHeading, miniHeadingMaintain, miniHeadingFilter, miniHeadingAvailable;

    /* Manual Component */
    public AddBookPanel addBookPanel;
    public BookFilterPanel bookFilterPanel;
    public BookTable bookTable;

    /* Variable */

    public BookStore(String frameTitle) {
        super(frameTitle);

        this.setLayout(null);

        /* Step 1 : Creating JPanel - Main Panel */
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new java.awt.Color(11, 70, 117));
        mainPanel.setPreferredSize(new Dimension(1500, 1500)); /* Very Important : from this We get ScrollBar */
        this.add(mainPanel);

        /* Step 2 : Creating JScrollPane - Main ScrollPane */
        mainScrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setBounds(0, 0, 1600, 838); /* Faulty Operation (Not correct dimension) */
        mainScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(mainScrollPane);

        /* Step 3 : Adding Main Heading */
        mainHeading = new JLabel();
        mainHeading.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 36));
        mainHeading.setText("Admin Panel");
        mainHeading.setForeground(new Color(255, 255, 255));
        mainHeading.setBounds(700, 20, 500, 36);
        mainPanel.add(mainHeading);

        /* Step : adding a mini heading : Maintain */
        miniHeadingMaintain = new JLabel();
        miniHeadingMaintain.setText("--- Maintain Book ---");
        miniHeadingMaintain.setFont(new Font("Yu Gothic UI Bold", Font.BOLD, 18)); // NOI18N
        miniHeadingMaintain.setForeground(new Color(255, 255, 255));
        miniHeadingMaintain.setBounds(45, 45, 200, 35);
        mainPanel.add(miniHeadingMaintain);

        /* Step 4 : adding Add Book  Form  */
        addBookPanel = new AddBookPanel(this);
        addBookPanel.setLayout(null);
        addBookPanel.setVisible(true);
        addBookPanel.setBounds(40, 90, 1500, 400);
        addBookPanel.setBackground(new Color(240, 240, 140, 185));
        addBookPanel.setForeground(new Color(25, 0, 0));
        mainPanel.add(addBookPanel);

        /* Step : adding a mini heading : filterBook */
        miniHeadingFilter = new JLabel();
        miniHeadingFilter.setText("--- Filter Book ---");
        miniHeadingFilter.setFont(new Font("Yu Gothic UI Bold", Font.BOLD, 18)); // NOI18N
        miniHeadingFilter.setForeground(new Color(255, 255, 255));
        miniHeadingFilter.setBounds(45, 510, 200, 35);
        mainPanel.add(miniHeadingFilter);

        /* Step 5 : adding Filter panel */
        bookFilterPanel = new BookFilterPanel(this);
        bookFilterPanel.setLayout(null);
        bookFilterPanel.setVisible(true);
        bookFilterPanel.setBounds(300, 550, 1000, 50);
        bookFilterPanel.setBackground(new Color(240, 240, 140, 185));
        bookFilterPanel.setForeground(new Color(25, 0, 0));
        mainPanel.add(bookFilterPanel);

        /* Step : adding a mini heading : Available Book */
        miniHeadingAvailable = new JLabel();
        miniHeadingAvailable.setText("--- Available Book ---");
        miniHeadingAvailable.setFont(new Font("Yu Gothic UI Bold", Font.BOLD, 18)); // NOI18N
        miniHeadingAvailable.setForeground(new Color(255, 255, 255));
        miniHeadingAvailable.setBounds(45, 595, 200, 35);
        mainPanel.add(miniHeadingAvailable);

        /* Step 5 : adding Available panel */
        bookTable = new BookTable(this);
        bookTable.setLayout(null);
        bookTable.setVisible(true);
        bookTable.setBounds(40, 645, 1500, 400);
        bookTable.setBackground(new Color(240, 240, 140, 185));
        bookTable.setForeground(new Color(25, 0, 0));
        mainPanel.add(bookTable);

        /* Here I am Adding Logical Code (which is not suitable)
         * What ? : Fetched all Data from DataBase whenever New Window  / Program starts
         *  */

        try {
            BookActionListener fetchData =new BookActionListener(this);
            fetchData.FetchAllBooks();
        } catch (Exception e) {
            System.out.println("Error at Fetching from frontend : " + e.getMessage());
        }

        /* Setting Image Icon at Title Bar */
        ImageIcon icon=new ImageIcon("src\\assets\\BookStoreIcon.png");
        this.setIconImage(icon.getImage());

        /* Closing Validation */
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                WindowClosingListener.ValidateWindowClosing(BookStore.this);
            }

        });

        /* Temporary closing event */
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    } /* Constructor close here*/

}
