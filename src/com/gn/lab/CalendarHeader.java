/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gn.lab;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.time.LocalDate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/12/2018
 */
public class CalendarHeader extends Label {

    private LocalDate date;

    CalendarHeader(int id, LocalDate date, String text) {
        setPrefSize(40, 40);
        date = date;
        setId(String.valueOf(id));
        setText(text);
    }

    CalendarHeader(String id, LocalDate date, String text) {
        setPrefSize(40, 40);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color : red");
        date = date;
        setId(String.valueOf(id));
        setText(text);
    }
}
