package com.test.figures.dto;

import com.test.figures.entity.figures.Figure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FigureDtoExtended extends FigureDto {
    private LocalDate createdDate;
    private String createdBy;
    private int version;
    private LocalDate lastModifiedDate;
    private String lastModifiedBy;

    public FigureDtoExtended(Figure figure) {
        super(figure);
        this.createdDate = figure.getCreatedDate();
        this.createdBy = figure.getCreatedBy();
        this.version = figure.getVersion();
        this.lastModifiedDate = figure.getLastModifiedDate();
        this.lastModifiedBy = figure.getLastModifiedBy();
    }
}
