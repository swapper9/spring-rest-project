package ru.swap.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component(value = "sampleBean")
public class SampleBean {

    private String stringValue;
    private int number;

    public SampleBean() {
    }

    @PostConstruct
    public void init() {
        stringValue = "postConstructValue";
        number = 666;
    }

}
