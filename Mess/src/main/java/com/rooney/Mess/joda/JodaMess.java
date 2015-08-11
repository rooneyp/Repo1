package com.rooney.Mess.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class JodaMess {
    public static void main(String[] args) {
//        misc();

//        parseISODateTimeAsUTLStoreAndRetrieveAsUTC();
        parseDateAndStoreAndRetrieve();
    }
    
    private void learnDateTime() {
        //        new DateTime("2015-06-16T12:33:24.051+01:00", DateTimeZone.UTC);
                DateTime dateTimeIST = new DateTime("2015-06-16T12:33:24.051+01:00", DateTimeZone.forID("Europe/Dublin"));
                DateTime dateTimeUTC = new DateTime("2015-06-16T12:33:24.051+01:00", DateTimeZone.UTC);
                DateTime dateTimeISTConvertedToUTC = new DateTime(dateTimeIST, DateTimeZone.UTC);
                
                System.out.println("dateTimeUTC \t\t\t" + dateTimeUTC + " " + dateTimeUTC.getZone());
                System.out.println("dateTimeIST \t\t\t" + dateTimeIST + " " + dateTimeIST.getZone());
                System.out.println("dateTimeISTConvertedToUTC \t" + dateTimeISTConvertedToUTC + " " + dateTimeISTConvertedToUTC.getZone());
    }    

    private static void misc() {
        DateTime dtDefault = new DateTime();
        System.out.println("default dateTime: " +dtDefault); 
        System.out.println(dtDefault.getChronology()); 

        DateTime dtUTC = new DateTime(DateTimeZone.UTC);
        System.out.println("\ndtUTC: " + dtUTC); 
        System.out.println(dtUTC.getChronology()); 

        LocalDateTime localDateTimeDefault = new LocalDateTime();
        System.out.println("\nlocalDateTimeDefault: " + localDateTimeDefault); 
        System.out.println(localDateTimeDefault.getChronology()); 

        LocalDateTime localDateTimeUTC = new LocalDateTime(DateTimeZone.UTC);
        System.out.println("\nlocalDateTimeUTC: " + localDateTimeUTC); 
        System.out.println(localDateTimeUTC.getChronology()); 


        DateTimeFormatter fmt = ISODateTimeFormat.dateTime(); // Factory that creates instances of DateTimeFormatter for the ISO8601 standard.
        System.out.println(fmt.print(dtDefault)); // 2015-06-16T12:33:24.051+01:00
        System.out.println(fmt.print(dtUTC)); // 2015-06-16T11:35:47.796Z
        System.out.println(fmt.print(localDateTimeDefault)); // 2015-06-16T11:35:47.796Z

        // PARSE in UTC??

        // PARSE in IST using ISO8601
        // DateTime isoIST = new DateTime("2015-06-16T12:33:24.051+01:00"); //in IST
        // System.out.println("isoIST: " + isoIST); //isoIST: 2015-06-16T12:33:24.051+01:00
    }

    private static void parseISODateTimeAsUTLStoreAndRetrieveAsUTC() {
        String isoIST = "2015-06-16T12:33:24.051+01:00";
        System.out.println("isoIST: " + isoIST);
        DateTime isoISTParsedAsUTC = new DateTime(isoIST, DateTimeZone.UTC); // in IST, parsed as UTC
        System.out.println("isoISTParsedAsUTC: " + isoISTParsedAsUTC);

        // DateTime isoISTConvToUTC = isoIST.toDateTime(DateTimeZone.UTC); //isoISTConvToUTC: 2015-06-16T11:33:24.051Z
        // System.out.println("isoISTConvToUTC: " + isoISTConvToUTC);

        // convert to utc millis
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(isoISTParsedAsUTC.getMillis());

        // convert back to DateTime-UTC
        DateTime dateTimeFromTimeStamp = new DateTime(sqlTimestamp.getTime(), DateTimeZone.UTC);

        // output as ISO8601
        System.out.println("dateTimeFromTimeStamp: " + dateTimeFromTimeStamp);
    }
    
    
    private static void parseDateAndStoreAndRetrieve() {
        String isoIST = "2015-06-16";
        System.out.println("isoIST: " + isoIST);
        LocalDate isoParsed = new LocalDate(isoIST);
        System.out.println("isoParsed: " + isoParsed);
        
        // DateTime isoISTConvToUTC = isoIST.toDateTime(DateTimeZone.UTC); //isoISTConvToUTC: 2015-06-16T11:33:24.051Z
        // System.out.println("isoISTConvToUTC: " + isoISTConvToUTC);
        
        // convert to utc millis
        java.sql.Date sqlDate = new java.sql.Date(isoParsed.toDate().getTime());
        
        
        // convert back to DateTime
        LocalDate dateTimeFromTimeStamp = new LocalDate(sqlDate.getTime());
        
        // output as ISO8601
        System.out.println("dateTimeFromTimeStamp: " + dateTimeFromTimeStamp);
    }
}
