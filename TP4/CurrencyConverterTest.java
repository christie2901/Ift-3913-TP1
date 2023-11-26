package test;

import currencyConverter.MainWindow;

import java.util.ArrayList;

public class CurrencyConverterTest {

    //Jeu de test pour les montants
    //{-500 000, -1, 0, 500 000, 1 000 000, 1 000 001, 1 500 000}
    //Jeu de test pour les devises
    //{CNY, USD, JPY, EUR}
    //{{USD,EUR}, {USD,CNY}, {CNY,JPY}}

    public void testCurrencyConverterConvert(){
        ArrayList<currencyConverter.Currency> currencies = new ArrayList<>();
        currencies.add(new currencyConverter.Currency("US Dollar", "USD"));
        currencies.add(new currencyConverter.Currency("Euro", "EUR"));
        currencies.add(new currencyConverter.Currency("Japanese Yen", "JPY"));
        currencies.add(new currencyConverter.Currency("Chinese Yuan Renminbi", "CNY"));

        assert(MainWindow.convert("US Dollar","Euro",currencies, -500000.0) == -1.0);
        assert(MainWindow.convert("US Dollar","Euro",currencies, -1.0) == -1.0);
        assert(MainWindow.convert("US Dollar","Euro",currencies, 0.0) == 0.0);
        assert(MainWindow.convert("US Dollar","Euro",currencies, 500000.0) == 465000.0);
        assert(MainWindow.convert("US Dollar","Euro",currencies, 1000000.0) == 930000.0);
        assert(MainWindow.convert("US Dollar","Euro",currencies, 1000001.0) == -1);
        assert(MainWindow.convert("US Dollar","Euro",currencies, 1500000.0) == -1);

        assert(MainWindow.convert("US Dollar","Chinese Yuan Renminbi",currencies, 500000.0) == -1);
        assert(MainWindow.convert("Japanese Yen","Chinese Yuan Renminbi",currencies, 500000.0) == -1);

    }


}
