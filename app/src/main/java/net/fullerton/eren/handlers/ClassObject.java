package net.fullerton.eren.handlers;

public class ClassObject {
    private String cNum;
    private String cDate;
    private String cRoom;
    private String cProf;

    //TODO: Add status
    public ClassObject(String cNum, String cDate, String cRoom, String cProf){
        this.cNum = cNum;
        this.cDate = cDate;
        this.cRoom = cRoom;
        this.cProf = cProf;
    }

    public String getNumber(){
        return cNum;
    }

    public String getDate(){
        return cDate;
    }

    public String getRoom(){
        return cRoom;
    }

    public String getProf(){
        return cProf;
    }
}
