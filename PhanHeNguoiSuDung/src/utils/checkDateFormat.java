package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class checkDateFormat {
	public static boolean checkDate(final String str)
	{
		if(str == null || str.equals(""))
		{
			return false;
		}
		
		

        try {

            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(str,
                    DateTimeFormatter.ofPattern("dd/MM/uuuu")
                            .withResolverStyle(ResolverStyle.STRICT)
            );

           

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
			
	}
}
