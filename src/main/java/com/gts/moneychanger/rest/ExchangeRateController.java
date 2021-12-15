package com.gts.moneychanger.rest;

import com.gts.moneychanger.dao.ExchangeRateDao;
import com.gts.moneychanger.entities.ExchangeRate;
import com.gts.moneychanger.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(value = "#{'${app.rest.api}'.concat('/')}" + ExchangeRateController.ctxController)
public class ExchangeRateController {

    @Autowired
    private ExchangeRateDao exchangeRateDao;

    @Value("#{'${app.rest.api}'.concat('/')}" + ExchangeRateController.ctxController)
    private String path;

    protected static final String ctxController = "exchangerate";

    @PostMapping
    public ResponseEntity<ExchangeRate> create(@RequestBody @Valid ExchangeRate exchangeRate, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            ExchangeRate savedExchangeRate = exchangeRateDao.save(exchangeRate);

            return ResponseEntity
                    .created(URI.create(path + "/" + savedExchangeRate.getId()))
                    .body(savedExchangeRate);
        }
        throw new BadRequestException(bindingResult);
    }

    @GetMapping("/{id}")
    public ExchangeRate read(@PathVariable int id) {
        return exchangeRateDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping
    public Iterable<ExchangeRate> readAll() {
        return exchangeRateDao.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRate> update(@RequestBody @Valid ExchangeRate exchangeRate, @PathVariable int id, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (exchangeRateDao.existsById(id)) {
                exchangeRate.setId(id);
                ExchangeRate savedExchangeRate = exchangeRateDao.save(exchangeRate);
                return ResponseEntity.ok(savedExchangeRate);
            }
            return ResponseEntity.notFound().build();
        }
        throw new BadRequestException(bindingResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if (exchangeRateDao.existsById(id)) {
            exchangeRateDao.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cc/{currencyCode}")
    public ExchangeRate readByCurrencyCode(@PathVariable String currencyCode) {
        return exchangeRateDao.findExchangeRateByCurrencyCode(currencyCode)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

}