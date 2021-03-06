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
package com.gn.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/02/2019
 */
public class MonetaryField extends TextField {

    private ObjectProperty<BigDecimal> value = new SimpleObjectProperty<>(this, "value");

    public MonetaryField(){

        this.getStyleClass().add("gn-monetary-field");

        this.getStylesheets().add(MonetaryField.class.getResource("/com/gn/css/simple.css").toExternalForm());

        this.setTextFormatter(new TextFormatter<>(change -> {
            if(change.getText().matches("[^0-9.,]") ){
                return null;
            } else
                return change;
        }));

        this.textProperty().addListener((observable, oldValue, newValue) -> {

//                System.out.println("newValue = " + newValue.length());
//                System.out.println("oldValue = " + oldValue.length());
//                System.out.println("-------------------------------");

            DecimalFormat s = (DecimalFormat) DecimalFormat.getInstance();

            char group = s.getDecimalFormatSymbols().getGroupingSeparator();
            char decimal = s.getDecimalFormatSymbols().getMonetaryDecimalSeparator();

            if(newValue.length() > 2) {
                String value = newValue;
                value = value.replaceAll("[^0-9]", "");

                String cent = value.substring(value.length() - 2);
                String middle = value.substring(0, value.length() - 2);

                StringBuilder build = new StringBuilder(middle);
                for (int i = middle.length(); i > 3; i -= 3) {
                    build.insert(i - 3, group);
                }
                String all;

                if(build.toString().length() > 0) all = build.toString() + decimal + cent;
                else all = build.toString() + cent;

                MonetaryField.super.setText(all);

                if(MonetaryField.super.getLength() > 2) {
                    setValue(new BigDecimal(build.toString().replaceAll("[^0-9]", "") + "." + cent));
                } else {
                    setValue(new BigDecimal(value.replaceAll("[^0-9]", "") ));
                }
            } else {
                String value = newValue.replaceAll("[^0-9]", "");
                MonetaryField.super.setText(value);
                if(newValue.isEmpty())  {
                    setValue(new BigDecimal(0));
                } else {
                    setValue(new BigDecimal(value));
                }
            }
        });

        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.BACK_SPACE){
                if(MonetaryField.super.getCaretPosition() > 0) {

                    if (MonetaryField.super.getText().
                            substring(MonetaryField.super.getCaretPosition() - 1,
                                    MonetaryField.super.getCaretPosition()).equals(".")) {
                        MonetaryField.super.deleteText(MonetaryField.super.getCaretPosition() - 2, MonetaryField.super.getCaretPosition());
                    } else if (MonetaryField.super.getText().substring(MonetaryField.super.getCaretPosition() - 1,
                            MonetaryField.super.getCaretPosition()).equals(",")) {
                        System.out.println("Aki");
                        MonetaryField.super.deleteText(MonetaryField.super.getCaretPosition() - 2, MonetaryField.super.getCaretPosition());
                    }
                }
            }
        });
    }

    @Override
    public String getText(int start, int end) {
        if (start > end) {
            if(this.getCaretPosition() != 0) {
                return getContent().get(start - 1, end);
            } else {
                return getContent().get();
            }
        }

        if (start < 0
                || end > getLength()) {
            throw new IndexOutOfBoundsException();
        }

        return getContent().get(start, end);
    }


    public final BigDecimal getValue() {
        return value.get();
    }

    public final void setValue(BigDecimal value) {
        this.value.set(value);
    }

    public final ObjectProperty<BigDecimal> valueProperty() {
        return value;
    }
}