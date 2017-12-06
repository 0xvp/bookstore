package se.contribe.bookstore.frontend.core;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static se.contribe.bookstore.frontend.core.BigDecimalUtils.parseBigDecimal;

public class BigDecimalUtilsTest {

    @Test
    public void testParseBigDecimal() throws Exception {
        assertThat(parseBigDecimal("10.0"), is(new BigDecimal("10.0")));
    }
}
