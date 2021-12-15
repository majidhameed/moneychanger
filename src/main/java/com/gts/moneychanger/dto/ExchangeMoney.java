package com.gts.moneychanger.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeMoney {

    @NotNull(message = "givenAmount must be provided")
    @Positive(message = "givenAmount must be positive")
    @Digits(integer = 9, fraction = 4)
    private BigDecimal givenAmount;

    @NotBlank(message = "givenCurrencyCode must be provided")
    @Size(message = "givenCurrencyCode must be 3 characters", min = 3, max = 3)
    private String givenCurrencyCode;

    private BigDecimal convertedAmount;

    @NotBlank(message = "convertCurrencyCode code must be provided")
    @Size(message = "convertCurrencyCode must be 3 characters", min = 3, max = 3)
    private String convertCurrencyCode;

    private BigDecimal exchangeRate;

    public ExchangeMoney(ExchangeMoney givenMoney, BigDecimal convertedAmount, BigDecimal exchangeRate) {
        this.givenAmount = givenMoney.getGivenAmount();
        this.givenCurrencyCode = givenMoney.getGivenCurrencyCode();
        this.convertCurrencyCode = givenMoney.getConvertCurrencyCode();
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
    }
}
