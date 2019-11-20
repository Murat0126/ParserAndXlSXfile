package network;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveImageFromUrl {


    public void saveImage(String imageUrl, String destinationFile)  {

//        try{

//            try(InputStream in = new URL(imageUrl).openStream()){
//                Files.copy(in, Paths.get("D:\\java\\XLSXexsmple\\image.png"));
//                System.out.println("Файл успешно загружен!!");
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("Не получилось");
//            }


//            URL url = new URL(imageUrl);
//            InputStream is = url.openStream();
//            OutputStream os = new FileOutputStream(destinationFile);
//
//            byte[] b = new byte[2048];
//            int length;
//
//            while ((length = is.read(b)) != -1) {
//                os.write(b, 0, length);
//            }
//
//            is.close();
//            os.close();

//        }
//        catch (IOException i){
//            System.out.println("ERROR:  " + i);
//        }

    }

}