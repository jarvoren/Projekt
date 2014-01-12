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
            return "2013";
        } else {
            return "2013-12-14";
        }
    }
}
