package se.contribe.bookstore.frontend.core;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class BigDecimalUtils {
    private BigDecimalUtils() {
        // util class
    }

    /**
     * Function for parsing Strings to BigDecimal
     *
     * @param decimalString Stringrepresentation of BigDecimal
     * @return BigDecimal from given String
     * @throws ParseException when String could not be parsed
     * @throws NullPointerException when given String is null
     */
    public static BigDecimal parseBigDecimal(String decimalString) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        return (BigDecimal) decimalFormat.parse(decimalString);
    }
}
