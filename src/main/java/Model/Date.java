package Model;

import java.io.Serializable;

//----------------------------------------------------Class Date----------------------------------------------------//
public class Date implements Serializable {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

//----------------------------------------------------Getters----------------------------------------------------//
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

//----------------------------------------------------Setters----------------------------------------------------//
    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

//----------------------------------------------------ToString----------------------------------------------------//
    public String toString(){
        StringBuilder dateString = new StringBuilder();
        if(day < 10)
            dateString.append("0");
        dateString.append(day).append("/");
        if(month < 10)
            dateString.append("0");
        dateString.append(month).append("/").append(year);
        return dateString.toString();
    }

//----------------------------------------------------Equals----------------------------------------------------//
    @Override
    public boolean equals(Object object){
        if(object instanceof  Date){
            Date date = (Date) object;
            return this.day == date.day && this.month == date.month && this.year == date.year;
        }
        return false;
    }

//----------------------------------------------------Compare----------------------------------------------------//
//Desc: if this < date => return -1
//      if this = date => return 0
//      if this > date => return 1
    public int compare(Date date){
        if(this.year < date.getYear())
            return -1;
        else
            if(this.year > date.getYear())
                return 1;
            else
                if(this.month < date.getMonth())
                    return -1;
                else
                    if(this.month > date.getMonth())
                        return 1;
                    else
                        if(this.day < date.getDay())
                            return -1;
                        else
                            if(this.day > date.getDay())
                                return 1;
                            else return 0;

    }
}

