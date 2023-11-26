package test;

import currencyConverter.Currency;

public class CurrencyTest {

    //Jeu de test pour les montants
    //{-500 000, -1, 0, 500 000, 1 000 000, 1 000 001, 1 500 000}

    public void testCurrencyConvert(){
        double exchangeValue = 0.71;

        assert (Currency.convert(-500000.0,exchangeValue) == -1.0);
        assert (Currency.convert(-1.0,exchangeValue) == -1.0);
        assert (Currency.convert(0.0,exchangeValue) == 0.0);
        assert (Currency.convert(500000.0,exchangeValue) == 355000.0);
        assert (Currency.convert(1000000.0,exchangeValue) == 710000.0);
        assert (Currency.convert(1000001.0,exchangeValue) == -1.0);
        assert (Currency.convert(1500000.0,exchangeValue) == -1.0);

    }

}
