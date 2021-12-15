package com.gts.moneychanger.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "exchangerates",
        indexes = {@Index(name = "ix_currency_code", columnList = "currency_code", unique = true)}
)

public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "currencyCode must be provided")
    @Column(length = 3, nullable = false, unique = true, name = "currency_code")
    @Size(message = "currencyCode must be 3 characters", min = 3, max = 3)
    private String currencyCode;

    private String currencyTitle;

    @Digits(integer = 9, fraction = 4)
    @NotNull(message = "buyRate must be provided")
    @Positive(message = "buyRate must be positive")
    private BigDecimal buyRate;

    @Digits(integer = 9, fraction = 4)
    @NotNull(message = "sellRate must be provided")
    @Positive(message = "sellRate must be positive")
    private BigDecimal sellRate;

    @PrePersist
    private void prePersist() {
        this.currencyCode = this.currencyCode.toUpperCase();
    }

}
