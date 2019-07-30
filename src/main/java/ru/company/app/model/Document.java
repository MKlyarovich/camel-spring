package ru.company.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    private Long id;

    @NotNull
    @NotBlank
    private String number;

    @NotNull
    private Date date;
}