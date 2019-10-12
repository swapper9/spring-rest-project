package ru.swap.bean;

import java.io.Serializable;

public class DBLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String logstring;

    public DBLog() {
    }

    public DBLog (int idLog, String logString) {
        this.id = idLog;
        this.logstring = logString;
    }

    public int getId() {
        return id;
    }

    public void setId(int iDLOG) {
        id = iDLOG;
    }

    public String getLogstring() {
        return logstring;
    }

    public void setLogstring(String lOGSTRING) {
        logstring = lOGSTRING;
    }
}
