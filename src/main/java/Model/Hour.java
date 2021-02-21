package Model;

import java.io.Serializable;

//----------------------------------------------------Class Hour----------------------------------------------------//
public class Hour implements Serializable {
    private int hours;
    private int minutes;

    public Hour(int hours, int minutes){
        this.hours = hours;
        this.minutes = minutes;
    }

//----------------------------------------------------Getters----------------------------------------------------//
    public int getHours(){
        return hours;
    }

    public int getMinutes(){
        return minutes;
    }

//----------------------------------------------------Setters----------------------------------------------------//
    public void setHours(int hours){
        this.hours = hours;
    }

    public void setMinutes(int minutes){
        this.minutes = minutes;
    }

//----------------------------------------------------ToString----------------------------------------------------//
    public String toString(){
        StringBuffer hourString = new StringBuffer();
        if(hours < 10)
            hourString.append("0").append(hours).append(":");
        else
            hourString.append(hours).append(":");
        if(minutes < 10)
            hourString.append("0").append(minutes);
        else
            hourString.append(minutes);
        return hourString.toString();
    }

//----------------------------------------------------Equals----------------------------------------------------//
    @Override
    public boolean equals(Object object){
        if(object instanceof  Hour){
            Hour hour = (Hour) object;
            return this.hours == hour.hours && this.minutes == hour.minutes;
        }
        return false;
    }

//----------------------------------------------------Compare----------------------------------------------------//
//Desc: if this < hour => return -1
//      if this = hour => return 0
//      if this > hour => return 1
    public int compare(Hour hour)
    {
        if(this.hours < hour.hours)
            return -1;
        else
            if(this.hours > hour.getHours())
                return 1;
            else
                if(this.minutes < hour.getMinutes())
                    return -1;
                else
                    if(this.minutes > hour.getMinutes())
                        return 1;
                    else
                        return 0;
    }
}
