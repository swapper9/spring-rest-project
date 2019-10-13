package ru.swap.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class DBLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String logstring;

    public DBLog() {
    }

}
