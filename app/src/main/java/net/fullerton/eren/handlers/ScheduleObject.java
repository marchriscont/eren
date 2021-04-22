package net.fullerton.eren.handlers;

public class ScheduleObject {
    private String sName;
    private String sDesc;
    private String sDate;
    private String sRoom;
    private String sProf;
    private String sUnits;
    private String sStatus; //Waitlisted, etc

    public ScheduleObject(String sName, String sDesc, String sDate, String sRoom, String sProf, String sUnits){ //TODO:add sStatus later
        this.sName = sName;
        this.sDesc = sDesc;
        this.sDate = sDate;
        this.sRoom = sRoom;
        this.sProf = sProf;
        this.sUnits = sUnits;
    }

    public String getName(){
        return sName;
    }

    public String getDesc(){
        return sDesc;
    }

    public String getDate(){
        return sDate;
    }

    public String getRoom(){
        return sRoom;
    }

    public String getProf(){
        return sProf;
    }

    public String getUnits(){
        return sUnits;
    }
}
