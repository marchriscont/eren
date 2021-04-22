package net.fullerton.eren.handlers;

public class RateObject {
    private String sRating;
    private String sDate;
    private String sDesc;

    public RateObject(String sRating, String sDate, String sDesc){
        this.sRating = sRating;
        this.sDate = sDate;
        this.sDesc = sDesc;
    }

    public String getRating(){
        return sRating;
    }

    public String getDate(){
        return sDate;
    }

    public String getDesc(){
        return sDesc;
    }
}
