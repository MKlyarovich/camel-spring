package ru.company.app.model;

import lombok.Data;

import java.util.Date;

@Data
public class Document {
    private Long id;
    private String number;
    private Date date;
}