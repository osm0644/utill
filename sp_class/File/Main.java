package sp.File;

import java.io.*;

public class Main {

    static String rootPath = ".\\Input";

    //binary 파일 읽는 방법

    static void FileSearchAll(String path){

        File directory = new File(path);
        File[] fList = directory.listFiles();

        for(File file : fList){
            if(file.isDirectory()){
                FileSearchAll(file.getPath());
            }
            else {
                String partPath = path.substring(rootPath.length());
                System.out.println("." + partPath + "\\"+ file.getName());
                if((file.length()/1024) > 3 ){
                    CopyFile(partPath, file.getName());
                }

            }

        }
    }
    static void CopyFile(String parPath, String filename){
        final int BUFFER_SIZE = 512;

        int readLen;

        try {
            File outputFolder = new File(".\\OUTPUT" + parPath);
            if(!outputFolder.exists()){
                outputFolder.mkdirs();
            }

            InputStream inputStream = new FileInputStream(".\\INPUT" + parPath + "\\" + filename);
            OutputStream outputStream = new FileOutputStream(".\\OUTPUT" + parPath + "\\" + filename);

            byte[] buffer = new byte[BUFFER_SIZE];

            while((readLen = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, readLen);
            }

            inputStream.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception{

        FileSearchAll(rootPath);
    }
}
