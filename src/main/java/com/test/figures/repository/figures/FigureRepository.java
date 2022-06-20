package com.test.figures.repository.figures;

import com.test.figures.entity.figures.Figure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FigureRepository extends JpaRepository<Figure, Integer> {
}
