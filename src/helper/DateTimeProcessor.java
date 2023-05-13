package helper;


import java.sql.Timestamp;
import java.time.*;


/** Holds methods for converting time. */
public class DateTimeProcessor {

    /** Converts a LocalDateTime object to a Timestamp object.
     * @param ldt the LocalDateTime object to convert.
     * @return The converted Timestamp object.
     */
    public static Timestamp toTimestamp(LocalDateTime ldt){

        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime startZDT = ZonedDateTime.of(ldt, localZoneId);
        ZonedDateTime startUTC = startZDT.withZoneSameInstant(ZoneOffset.UTC);

        LocalDateTime tsDateTime = startUTC.toLocalDateTime();

        return Timestamp.valueOf(tsDateTime);
    }

    /** Converts a LocalDateTime object to UTC.
     * @param ldt the LocalDateTime object to convert.
     * @return The converted LocalDateTime object.
     */
    public static LocalDateTime toUTC(LocalDateTime ldt){

        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localZDT = ZonedDateTime.of(ldt, localZoneId);
        ZonedDateTime startUTC = localZDT.withZoneSameInstant(ZoneOffset.UTC);

        LocalDateTime UTCDateTime = startUTC.toLocalDateTime();

        return UTCDateTime;
    }

    /** Converts a LocalDateTime object to the user's local timezone.
     * @param ldt the LocalDateTime object to convert.
     * @return The converted LocalDateTime object.
     */
    public static LocalDateTime toLocalZone(LocalDateTime ldt){
        LocalDateTime utcLDT = ldt;
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime startUTC = ZonedDateTime.of(utcLDT, utcZone);
        ZonedDateTime localZDT = startUTC.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localZonedTime = localZDT.toLocalDateTime();

        return localZonedTime;
    }

    //For converting date and time combo boxes into a LocalDateTime to make an Appointment.
    /** Converts a LocalDate and time in hours and minutes to a LocalDateTime object.
     * @param date The LocalDate to convert.
     * @param hours The hours to use for the converted LocalDateTime.
     * @param minutes The minutes to use for the converted LocalDateTime.
     * @return The resulting LocalDateTime object.
     */
    public static LocalDateTime toLDT(LocalDate date, String hours, String minutes){
        LocalDateTime ldt = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), Integer.parseInt(hours), Integer.parseInt(minutes));

        return ldt;
    }

    //For converting current time to Est.
    /** Converts a LocalDateTime object to EST.
     * @param ldt the LocalDateTime object to convert.
     * @return The converted LocalDateTime object.
     */
    public static LocalDateTime toEST(LocalDateTime ldt){
        LocalDateTime currentTime = ldt;
        ZoneId estId = ZoneId.of("America/New_York");
        ZonedDateTime current = ZonedDateTime.of(currentTime, ZoneId.systemDefault());
        ZonedDateTime est = current.withZoneSameInstant(estId);
        LocalDateTime currentInEST = est.toLocalDateTime();

        return currentInEST;
    }

}
