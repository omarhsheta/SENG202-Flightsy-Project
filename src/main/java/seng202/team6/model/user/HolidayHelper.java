package seng202.team6.model.user;

public class HolidayHelper {
    public HolidayHelper() {

    }

    public static boolean IsValidDate(int day, int month, int year, int hour, int minute) {
        if (day > 31 || day < 1) {
            return false;
        }

        if (month > 12 || month < 1) {
            return false;
        }

        if (hour > 24 || hour < 0) {
            return false;
        }

        if (minute > 60 || minute < 0) {
            return false;
        }

        return true;
    }
}
