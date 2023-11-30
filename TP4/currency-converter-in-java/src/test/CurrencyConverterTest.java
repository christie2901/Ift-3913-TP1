package test;

import currencyConverter.MainWindow;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverterTest {

    //Tests boîte noire

    //Jeu de test pour les montants
    //{-500 000, -1, 0, 500 000, 1 000 000, 1 000 001, 1 500 000}
    //Jeu de test pour les devises
    //{CNY, USD, JPY, EUR}
    //{{USD,EUR}, {USD,CNY}, {CNY,JPY}}
    static ArrayList<currencyConverter.Currency> currencies = currencyConverter.Currency.init();

    @Test
    public void testBoiteNoireCurrencyConverter(){
        assertEquals(MainWindow.convert("US Dollar","Euro",currencies, -500000.0), -1.0);
        assertEquals(MainWindow.convert("US Dollar","Euro",currencies, -1.0) , -1.0);
        assertEquals(MainWindow.convert("US Dollar","Euro",currencies, 0.0) , 0.0);
        assertEquals(MainWindow.convert("US Dollar","Euro",currencies, 500000.0) , 465000.0);
        assertEquals(MainWindow.convert("US Dollar","Euro",currencies, 1000000.0) , 930000.0);
        assertEquals(MainWindow.convert("US Dollar","Euro",currencies, 1000001.0) , -1);
        assertEquals(MainWindow.convert("US Dollar","Euro",currencies, 1500000.0) , -1);

        assertEquals(MainWindow.convert("US Dollar","Chinese Yuan Renminbi",currencies, 500000.0) , -1);
        assertEquals(MainWindow.convert("Japanese Yen","Chinese Yuan Renminbi",currencies, 500000.0) , -1);

    }

    //Tests boîte blanche

    @Test
    public void testBoiteBlancheCurrencyConvert(){
        double amount = 500.0;
        assertEquals(MainWindow.convert("British Pound","US Dollars",currencies, amount) , 0.0);
        assertEquals(MainWindow.convert("British Pound","US Dollar",currencies, amount) , 755.0);
        assertEquals(MainWindow.convert("US Dollar","British Pound",currencies, amount) , 330.0);
        assertEquals(MainWindow.convert("British Pound","Euro",currencies, amount) , 705.0);
        assertEquals(MainWindow.convert("Euro","British Pound",currencies, amount) , 355.0);
        assertEquals(MainWindow.convert("British Pound","Swiss Franc",currencies, amount) , 760.0);
        assertEquals(MainWindow.convert("Swiss Franc","British Pound",currencies, amount) , 330.0);
        assertEquals(MainWindow.convert("British Pound","Japanese Yen",currencies, amount) , 93205.0);
        assertEquals(MainWindow.convert("Japanese Yen","British Pound",currencies, amount) , 2.50);
        assertEquals(MainWindow.convert("British Pound","US Dollars",currencies, amount) , 0.0);
        assertEquals(MainWindow.convert("US Dollars","British Pound",currencies, amount) , 0.0);
    }

}
