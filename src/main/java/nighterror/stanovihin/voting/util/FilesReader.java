package nighterror.stanovihin.voting.util;

import java.io.*;

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

    public static String readFileContent(InputStream is) {
        StringBuilder stringBuilder = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
