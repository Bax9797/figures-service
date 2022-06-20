package com.test.figures.entity.figures;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Accessors(chain = true)
@NoArgsConstructor
@DiscriminatorValue("SQUARE")
@Data
public class Square extends Figure {
    private double width;

    public Square(LocalDate createdDate, String author, int version, LocalDate lastModification, String lastModifier, double width) {
        super(createdDate, author, version, lastModification, lastModifier);
        this.width = width;
    }

    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public double getArea() {
        return width * width;
    }

    @Override
    public Map<String, Double> getParameters() {
        return Map.of("width", width);
    }
}
