package Backend.FileManagment;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;

public class CredentialByFile {

    private static HashMap<String,String> credential=null;

    public static HashMap<String, String> getCredential() {

        try {

            FileReader fileReader = null;

            try {

                fileReader = new FileReader("./src/assets/Credential.props");

                Properties properties = new Properties();

                properties.load(fileReader);

                /* Adding Properties to HashMap */
                credential=new HashMap<>(2);

                for (HashMap.Entry<Object, Object> entry : properties.entrySet()) {
                    credential.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }

            } catch (Exception e) {
                System.out.println("Error in reading Credential : " + e);
            } finally {
                if (fileReader != null) {
                    fileReader.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error at Reading Credential file : " + e.getMessage());
        }

        return credential;
    }

}