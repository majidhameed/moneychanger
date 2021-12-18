package com.gts.moneychanger.service;

import com.gts.moneychanger.dao.ExchangeRateDao;
import com.gts.moneychanger.dto.ExchangeMoney;
import com.gts.moneychanger.entities.ExchangeRate;
import com.gts.moneychanger.exception.NotAcceptableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class MoneyExchangeService {

    @Autowired
    private ExchangeRateDao exchangeRateRepository;

    @Value("${app.basecurrency:'SGD'}")
    private String baseCurrency;

    public ExchangeMoney exchangeAmount(ExchangeMoney exchangeMoney) {
        log.info("Given: {}", exchangeMoney);
        if (exchangeMoney.getGivenCurrencyCode().equalsIgnoreCase(exchangeMoney.getConvertCurrencyCode())) {
            throw new NotAcceptableException(String.format("Given currency %s and convert currency %s is the same.",
                    exchangeMoney.getGivenCurrencyCode(), exchangeMoney.getConvertCurrencyCode()));
        }

        if (!baseCurrency.equalsIgnoreCase(exchangeMoney.getGivenCurrencyCode()) &&
                !baseCurrency.equalsIgnoreCase(exchangeMoney.getConvertCurrencyCode())) {
            throw new NotAcceptableException(String.format("Either given currency %s or convert currency %s should be in %s",
                    exchangeMoney.getGivenCurrencyCode(), exchangeMoney.getConvertCurrencyCode(), baseCurrency));
        }

        String lookupCurrencyCode = exchangeMoney.getConvertCurrencyCode();

        if (baseCurrency.equalsIgnoreCase(lookupCurrencyCode)) {
            lookupCurrencyCode = exchangeMoney.getGivenCurrencyCode();
        }

        ExchangeRate currencyExchangeRate = exchangeRateRepository
                .findExchangeRateByCurrencyCode(lookupCurrencyCode)
                .orElseThrow(() -> new NotAcceptableException(String.format("Required currency %s not found.",
                        baseCurrency.equalsIgnoreCase(exchangeMoney.getConvertCurrencyCode()) ?
                                exchangeMoney.getGivenCurrencyCode() : exchangeMoney.getConvertCurrencyCode())
                ));

        exchangeRateRepository
                .findExchangeRateByCurrencyCode(lookupCurrencyCode).get();
        log.info("Fetched currency exchange rate: {}", currencyExchangeRate);

        BigDecimal exchangeAmount = exchangeMoney.getGivenAmount();
        BigDecimal exchangedRate;
        if (baseCurrency.equalsIgnoreCase(exchangeMoney.getGivenCurrencyCode())) {
            exchangedRate = currencyExchangeRate.getSellRate();
            exchangeAmount = exchangeAmount.divide(exchangedRate, 2, RoundingMode.HALF_UP);
        } else {
            exchangedRate = currencyExchangeRate.getBuyRate();
            exchangeAmount = exchangeAmount.multiply(exchangedRate);
            exchangeAmount = exchangeAmount.setScale(2, RoundingMode.HALF_UP);
        }

        ExchangeMoney convertedMoney = new ExchangeMoney(exchangeMoney, exchangeAmount, exchangedRate);
        log.info("Returning: {}", convertedMoney);
        return convertedMoney;
    }

}
