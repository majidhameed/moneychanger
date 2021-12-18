package com.gts.moneychanger;

import com.gts.moneychanger.dao.ExchangeRateDao;
import com.gts.moneychanger.dto.ExchangeMoney;
import com.gts.moneychanger.entities.ExchangeRate;
import com.gts.moneychanger.service.MoneyExchangeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTests {

    @Autowired
    private MoneyExchangeService moneyExchangeService;

    @MockBean
    private ExchangeRateDao exchangeRateDao;

    private static Optional<ExchangeRate> exchangeRateUSD;
    private static Optional<ExchangeRate> exchangeRateHKD;

    @Before
    public void setup() {
        exchangeRateUSD = Optional.of(
                new ExchangeRate(
                        1, "USD", "United States Dollar", BigDecimal.valueOf(1.3392), BigDecimal.valueOf(1.3574)
                ));

        exchangeRateHKD = Optional.of(
                new ExchangeRate(
                        2, "HKD", "Hong Kong Dollar", BigDecimal.valueOf(0.1738), BigDecimal.valueOf(0.1698)
                ));
    }


    @Test
    public void testSGDtoUSD() {
        when(exchangeRateDao.findExchangeRateByCurrencyCode("USD")).thenReturn(exchangeRateUSD);
        ExchangeMoney exchangeMoney = new ExchangeMoney();
        exchangeMoney.setGivenAmount(BigDecimal.valueOf(200));
        exchangeMoney.setGivenCurrencyCode("SGD");
        exchangeMoney.setConvertCurrencyCode("USD");
        ExchangeMoney convertedMoney = moneyExchangeService.exchangeAmount(exchangeMoney);

        assertEquals(BigDecimal.valueOf(147.34), convertedMoney.getConvertedAmount());
        assertEquals(BigDecimal.valueOf(1.3574), convertedMoney.getExchangeRate());
        assertEquals(exchangeMoney.getConvertCurrencyCode(), convertedMoney.getConvertCurrencyCode());
    }

    @Test
    public void testSGDtoHKD() {
        when(exchangeRateDao.findExchangeRateByCurrencyCode("HKD")).thenReturn(exchangeRateHKD);
        ExchangeMoney exchangeMoney = new ExchangeMoney();
        exchangeMoney.setGivenAmount(BigDecimal.valueOf(200));
        exchangeMoney.setGivenCurrencyCode("SGD");
        exchangeMoney.setConvertCurrencyCode("HKD");
        ExchangeMoney convertedMoney = moneyExchangeService.exchangeAmount(exchangeMoney);

        assertEquals(BigDecimal.valueOf(1177.86), convertedMoney.getConvertedAmount());
        assertEquals(BigDecimal.valueOf(0.1698), convertedMoney.getExchangeRate());
        assertEquals(exchangeMoney.getConvertCurrencyCode(), convertedMoney.getConvertCurrencyCode());
    }

}
