package mpr.proj.pedigree;

import java.sql.Date;

public class DateOfBirth {
    private Date date;
    private Boolean yearOnly;

    public void setDate(Date date, Boolean yearOnly) {
        this.date = date;
        this.yearOnly = yearOnly;
    }
    public String getDate() {
        if (yearOnly) {
        	Integer rok = date.getYear();
            return rok.toString();
        } else {
            return date.toString();
        }
    }
}
