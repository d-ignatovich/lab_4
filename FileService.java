import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static FileService instance;
    private  static File file;
    private FileService(){}

    public static synchronized FileService getInstance() {
        if (instance == null) {
            instance = new FileService();
        }

        file = new File("data.cvs");
        if (!file.isFile()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return instance;
    }


    public List<Account> readFile() {
        FileReader reader = null;
        List<Account> allAccounts = new ArrayList<>();

        try {
            reader = new FileReader(file);
            int x = 0;
            String account = "";
            while ((x = reader.read()) != -1) {
                if (x == 59){
                    String[] ac = account.split(",");
                    ac[0] = ac[0].replaceAll("\n", "");
                    allAccounts.add(new Account(ac[0],ac[1],ac[2],ac[3],Boolean.parseBoolean(ac[4])));
                } else {
                    account += (char) x;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return allAccounts;
    }

    public void writeInFile(String data, boolean append){
        FileWriter writer = null;

        try {
            writer = new FileWriter(file, append);
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer!=null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}