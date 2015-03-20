package com.example.markelor.calculadora.logic;

/**
 * Created by markelor on 19/03/15.
 */
public class CalcLogic {

    private double _currentTotal;

    /**
     * Constructor
     */
    public CalcLogic() {
        _currentTotal = 0;
    }

    public String getTotalString() {
        return String.valueOf(_currentTotal);
    }

    public void setTotal(double d) {
        _currentTotal = d;
    }

    public double add(String n) {
        _currentTotal += convertToNumber(n);

        return _currentTotal;
    }

    public double subtract(String n) {
        _currentTotal -= convertToNumber(n);

        return _currentTotal;
    }

    public double multiply(String n) {
        _currentTotal *= convertToNumber(n);

        return _currentTotal;
    }

    public double divide(String n) {
        _currentTotal /= convertToNumber(n);

        return _currentTotal;
    }

    private double convertToNumber(String n) {
        return Double.parseDouble(n);
    }
}
