package nighterror.stanovihin.voting.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FilesReader {

    public static String readFile(String filePath){
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                fileContent.append(line).append("\n");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return fileContent.toString();

    }
}
