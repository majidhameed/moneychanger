package com.gts.moneychanger.dao;

import com.gts.moneychanger.entities.ExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateDao extends CrudRepository<ExchangeRate, Integer> {
    Optional<ExchangeRate> findExchangeRateByCurrencyCode(String currencyCode);
}
