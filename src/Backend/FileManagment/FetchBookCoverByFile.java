package Backend.FileManagment;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FetchBookCoverByFile {

    public static String fetchBookCoverFromDevice() {
        String bookPathCover="src\\assets\\byDefaultCover.jpg";

        try {
            /* File Chooser Prompt */
            JFileChooser browseImage = new JFileChooser("");

            /* Filter Image Extension (it is not working actually) */
            FileNameExtensionFilter onlyForImg = new FileNameExtensionFilter("Choose Book Cover", "png", "jpg", "jpeg");

            browseImage.addChoosableFileFilter(onlyForImg);

            int showOpenDialogue = browseImage.showOpenDialog(null);

            if (showOpenDialogue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = browseImage.getSelectedFile();

                bookPathCover = selectedFile.getAbsolutePath();
            }

        } catch (Exception e) {
            System.out.println("Error at Fetch cover : " + e + " Msg : " +e.getMessage());
        }

        return bookPathCover;
    }


}
