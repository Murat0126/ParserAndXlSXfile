package converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

    public String getDate(int amount ) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        return dateFormat.format(cal.getTime());

    }

}
