package helperLogics;

public class LogicHelper {


    public String extractDigits(String source)  {
        source = source.replaceAll("\\D+","");
        return source;
    }

    public String replace(String source, String delete)  {
        int startNum = delete.length();
        int endNum = source.length();
        /*оставить только часть текста от startNum до endNum*/
        source = source.substring(startNum, endNum);
        return source;
    }

    public String getFirstWord(String source, String delete)  {
        int endNum = delete.length();
        int startNum =0;
        /*оставить только часть текста от startNum до endNum*/
        source = source.substring(startNum, endNum);
        return source;
    }

    public Boolean check(String text, String wordToFind){
        return text.contains(wordToFind);
    }


}
