package com.estrella.PicoPlaca.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class PicoYPlacaService {
	
    // Method to check if a vehicle with a given license plate can drive on a specific date and time
    public String canDrive(String plate, String dateString, String timeString) throws ParseException {
    	
        // Initialize date and time formatters
        SimpleDateFormat dateSDF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm");
        
        // Convert date and time from string to Date object
        Date date = dateSDF.parse(dateString);
        Date time = timeSDF.parse(timeString);
        
        // Initialize calendar to find the day of the week
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        // Extract the day of the week and the last digit of the license plate
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int lastDigit = Integer.parseInt(plate.substring(plate.length() - 1));
        
        // Extract the time in milliseconds
        long inputTime = time.getTime();
        
        // Define the restricted times in milliseconds
        long morningStart = timeSDF.parse("06:00").getTime();
        long morningEnd = timeSDF.parse("09:30").getTime();
        long eveningStart = timeSDF.parse("16:00").getTime();
        long eveningEnd = timeSDF.parse("20:00").getTime();
        
        // Check if the time falls within the restricted time periods
        boolean restrictedTime = (inputTime >= morningStart && inputTime <= morningEnd)
                || (inputTime >= eveningStart && inputTime <= eveningEnd);

        // Check if the vehicle is restricted from driving during the restricted times on the specified day
        if (restrictedTime) {
            if ((dayOfWeek == Calendar.MONDAY && (lastDigit == 1 || lastDigit == 2))
                    || (dayOfWeek == Calendar.TUESDAY && (lastDigit == 3 || lastDigit == 4))
                    || (dayOfWeek == Calendar.WEDNESDAY && (lastDigit == 5 || lastDigit == 6))
                    || (dayOfWeek == Calendar.THURSDAY && (lastDigit == 7 || lastDigit == 8))
                    || (dayOfWeek == Calendar.FRIDAY && (lastDigit == 9 || lastDigit == 0))) {
                return "You cannot drive";
            }
        }
        
        return "You can drive";
    }
}


