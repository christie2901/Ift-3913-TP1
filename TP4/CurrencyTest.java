package test;

import currencyConverter.Currency;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {

    //Test boite noire
    //Jeu de test pour les montants
    //{-500 000, -1, 0, 500 000, 1 000 000, 1 000 001, 1 500 000}

    @Test
    static void testBoiteNoireCurrency(){
        double exchangeValue = 0.71;

        assertEquals (Currency.convert(-500000.0,exchangeValue) , -1.0);
        assertEquals (Currency.convert(-1.0,exchangeValue) , -1.0);
        assertEquals (Currency.convert(0.0,exchangeValue) , 0.0);
        assertEquals (Currency.convert(500000.0,exchangeValue) , 355000.0);
        assertEquals (Currency.convert(1000000.0,exchangeValue) , 710000.0);
        assertEquals (Currency.convert(1000001.0,exchangeValue) , -1.0);
        assertEquals (Currency.convert(1500000.0,exchangeValue) , -1.0);

    }

    @Test
    //Test boite blanche
    static void testBoiteBlancheCurrency(){
        assertEquals (Currency.convert(5000.0,0.74) , 3700.0);
    }


    public static void main(String[] args) {
        testBoiteNoireCurrency();
        System.out.println("Tests boîte noire réussis");
        testBoiteBlancheCurrency();
        System.out.println("Tests boîte blanche réussis");
    }
}
