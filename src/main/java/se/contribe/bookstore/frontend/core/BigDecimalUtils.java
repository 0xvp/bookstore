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
     *
     * @param decimalString
     * @return
     * @throws ParseException
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
