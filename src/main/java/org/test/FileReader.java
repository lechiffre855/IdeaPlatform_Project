package org.test;

import java.io.*;

public class FileReader {

    private String pathJsonFile;

    public FileReader(String pathJsonFile) {
        this.pathJsonFile = pathJsonFile;
    }

    public String readFile(){
        try (FileInputStream fileInputStream = new FileInputStream(pathJsonFile);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
