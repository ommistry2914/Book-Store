package Frontend.BooksPanel.AddBookPanel;

import Frontend.BookStore;
import com.raven.datechooser.DateChooser;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* It is a Form (Operation)  Frame */
public class AddBookPanel extends JPanel {

    /* Classes */
    BookStore mainContainer;


    /* Component */
    public JLabel lbBookID, lbBookName, lbBookSubject, lbAuthorName, lbPublication, lbDatePublication, lbBookPrice, lbBookQuantity, lbTotalCost;
    public JTextField tfBookID, tfBookName, tfBookSubject, tfAuthorName, tfPublication, tfDatePublication, tfTotalCost;
    public JSpinner spBookPrice, spBookQuantity;
    SpinnerModel valueOfPrice, valueOfQuantity;
    public ImageIcon calenderIcon;
    public JButton btnCalender;
    public DateChooser dateChooser;

    /* Button Panel*/
    public OperationButtonPanel operationButtonPanel;
    /* Image Panel*/
    public BookCover bookCover;

    /* Integer for book Price,Quantity,TotalCost*/
    int IntBookPrice = 200, IntBookQuantity = 1, IntBookTotalCost = IntBookPrice * IntBookQuantity;


    public AddBookPanel(BookStore mainContainer) {
        this.mainContainer = mainContainer;

        /* Step : Adding Label - TextField */

        /* Input 1 : Book ID */
        lbBookID = new JLabel();
        lbBookID.setText("Book ID");
        lbBookID.setFont(new java.awt.Font("Yu Gothic Medium", Font.BOLD, 18));
        lbBookID.setBounds(80, 20, 100, 35);
        this.add(lbBookID);

        /* Trying to add Formatted Text Field */
       /* NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        numberFormatter.setAllowsInvalid(false); //this is the key!!
        JFormattedTextField tfBookID = new JFormattedTextField(numberFormatter);*/

        tfBookID = new JTextField();
        tfBookID.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        tfBookID.setBounds(190, 20, 320, 30);
        this.add(tfBookID);

        /* In frontend, I am adding Validation (Not Recommended) (ID RegEx is not working properly ) */
        /* Here is one bug : alphabets are printed, it not stops automatically */

        tfBookID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent typedEvent) {

                try{

                    char keyChar = typedEvent.getKeyChar();

                    if (!(keyChar >= 48 && keyChar <= 57) && !(keyChar==8 || keyChar==127)) {
                        JOptionPane optionPane = new JOptionPane("ID can only be a number", JOptionPane.ERROR_MESSAGE);
                        JDialog dialog = optionPane.createDialog("Error!");
                        dialog.setAlwaysOnTop(true); // to show top of all other application
                        dialog.setVisible(true); // to visible the dialog
                    }
                } catch (Exception e) {
                    System.out.println("Error in Id regEx (Typed) : "+e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                try {

                    /* For Int : ID */
                    String regExOfID = "[0-9]+";

                    /* Checking Pattern */
                    Pattern pattern = Pattern.compile(regExOfID);
                    String Id = tfBookID.getText();
                    Matcher matcher = pattern.matcher(Id);

                    if (!matcher.matches()) {
                        JOptionPane optionPane = new JOptionPane("Id have only digits with minimum length 1!", JOptionPane.ERROR_MESSAGE);
                        JDialog dialog = optionPane.createDialog("Error!");
                        dialog.setAlwaysOnTop(true); // to show top of all other application
                        dialog.setVisible(true); // to visible the dialog

                        tfBookID.setFocusable(true);
                    }
                } catch (Exception es) {
                    System.out.println("Error in Id regEx (Released) : "+es);
                }

            }
        });

        /* Input 2 : Book Name */

        lbBookName = new JLabel();
        lbBookName.setText("Book Name");
        lbBookName.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbBookName.setBounds(580, 20, 150, 35);
        this.add(lbBookName);

        tfBookName = new JTextField();
        tfBookName.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        tfBookName.setBounds(710, 20, 320, 30);
        this.add(tfBookName);

        /* Input 3 : Book Subject */
        lbBookSubject = new JLabel();
        lbBookSubject.setText("Book Subject");
        lbBookSubject.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbBookSubject.setBounds(50, 90, 250, 35);
        this.add(lbBookSubject);

        tfBookSubject = new JTextField();
        tfBookSubject.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        tfBookSubject.setBounds(190, 90, 320, 30);
        this.add(tfBookSubject);


        /* Input 4 : Author Name */
        lbAuthorName = new JLabel();
        lbAuthorName.setText("Author Name");
        lbAuthorName.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbAuthorName.setBounds(570, 90, 150, 35);
        this.add(lbAuthorName);

        tfAuthorName = new JTextField();
        tfAuthorName.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        tfAuthorName.setBounds(710, 90, 320, 30);
        this.add(tfAuthorName);

        /* Input 5 : publication */
        lbPublication = new JLabel();
        lbPublication.setText("Publication");
        lbPublication.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbPublication.setBounds(50, 160, 250, 35);
        this.add(lbPublication);

        tfPublication = new JTextField();
        tfPublication.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        tfPublication.setBounds(190, 160, 320, 30);
        this.add(tfPublication);

        /* Input 6 : Date of Publication */
        lbDatePublication = new JLabel();
        lbDatePublication.setText("Date Publication");
        lbDatePublication.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbDatePublication.setBounds(550, 160, 150, 35);
        this.add(lbDatePublication);

        tfDatePublication = new JTextField();
        tfDatePublication.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        tfDatePublication.setBounds(710, 160, 285, 30);
        this.add(tfDatePublication); /* I have no added Listener here 'KeyListener' */

        calenderIcon = new ImageIcon("src\\assets\\calenderIcon.png");
        btnCalender = new JButton(calenderIcon);
        btnCalender.setBounds(1000, 160, 32, 32);
        this.add(btnCalender);

        try {
            dateChooser = new DateChooser();
            dateChooser.setDateFormat("yyyy-MM-dd");
            dateChooser.setTextRefernce(tfDatePublication);
            dateChooser.setForeground(new Color(0, 103, 184));
        } catch (Exception e) {
            System.out.println("Error In calender : " + e + " Msg : " + e.getMessage());
        }

        btnCalender.addActionListener(event -> {
            dateChooser.showPopup();
        });

        /* Input 7 : Price Of Book */
        lbBookPrice = new JLabel();
        lbBookPrice.setText("Book Price");
        lbBookPrice.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbBookPrice.setBounds(50, 240, 250, 35);
        this.add(lbBookPrice);

        valueOfPrice = new SpinnerNumberModel(200, 200, 1500, 50);
        spBookPrice = new JSpinner(valueOfPrice);
        spBookPrice.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        spBookPrice.setBounds(150, 240, 100, 30);
        this.add(spBookPrice);

        JComponent comp = spBookPrice.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        /* Input 8 : Quantity Of Book */
        lbBookQuantity = new JLabel();
        lbBookQuantity.setText("Book Quantity");
        lbBookQuantity.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbBookQuantity.setBounds(280, 240, 250, 35);
        this.add(lbBookQuantity);

        valueOfQuantity = new SpinnerNumberModel(1, 1, 250, 1);
        spBookQuantity = new JSpinner(valueOfQuantity);
        spBookQuantity.setFont(new Font("Trebuchet MS", Font.PLAIN, 18)); // NOI18N
        spBookQuantity.setBounds(420, 240, 100, 30);
        this.add(spBookQuantity);

        JComponent compForQuantity = spBookQuantity.getEditor();
        JFormattedTextField fieldForQuantity = (JFormattedTextField) compForQuantity.getComponent(0);
        DefaultFormatter formatterForQuantity = (DefaultFormatter) fieldForQuantity.getFormatter();
        formatterForQuantity.setCommitsOnValidEdit(true);

        /* Input 8 : Total Cost Of Book ( TotalCost = Price * Quantity) */
        lbTotalCost = new JLabel();
        lbTotalCost.setText("Total Cost");
        lbTotalCost.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
        lbTotalCost.setBounds(585, 240, 250, 35);
        this.add(lbTotalCost);

        tfTotalCost = new JTextField();
        tfTotalCost.setText("200");
        tfTotalCost.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
        tfTotalCost.setBounds(710, 240, 320, 30);
        tfTotalCost.setEditable(false);
        this.add(tfTotalCost);


        /* Listener for change of Total cost when one of 2 value changes */

        try {
            spBookPrice.addChangeListener(event -> {

                IntBookPrice = (int) spBookPrice.getValue();
                IntBookTotalCost = IntBookPrice * IntBookQuantity;
                tfTotalCost.setText("" + IntBookTotalCost);

            });
            spBookQuantity.addChangeListener(event -> {

                IntBookQuantity = (int) spBookQuantity.getValue();
                IntBookTotalCost = IntBookPrice * IntBookQuantity;
                tfTotalCost.setText("" + IntBookTotalCost);
            });
        } catch (Exception e) {
            System.out.println("Error In Change of total cost : " + e + " Msg : " + e.getMessage());
        }

        /* Adding Button Panel */
        operationButtonPanel = new OperationButtonPanel(mainContainer);
        operationButtonPanel.setBackground(new Color(174, 202, 153, 255));
        operationButtonPanel.setBounds(55, 325, 1000, 40);
        operationButtonPanel.setLayout(new GridLayout(1, 4, 20, 25));
        this.add(operationButtonPanel);

        /* Book cover Panel */
        bookCover = new BookCover(mainContainer);
        bookCover.setBounds(1090, 20, 350, 350);
        bookCover.setBackground(new Color(177, 190, 132, 255));
        bookCover.setLayout(null);
        this.add(bookCover);
    }
}
