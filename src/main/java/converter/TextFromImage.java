package converter;


import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;

import java.io.IOException;


public class TextFromImage {

    public String getImgText() {
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        tessBaseAPI.Init("D:\\java\\XLSXexsmple", "eng");

        lept.PIX image = lept.pixRead("D:\\java\\XLSXexsmple\\fromBAse64.png");

        tessBaseAPI.SetImage(image);

        BytePointer bytePointer = tessBaseAPI.GetUTF8Text();
        String textFromImage = bytePointer.getString();
        return textFromImage;
    }


}
