package com.gts.moneychanger.rest;

import com.gts.moneychanger.dto.ExchangeMoney;
import com.gts.moneychanger.exception.BadRequestException;
import com.gts.moneychanger.service.MoneyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "#{'${app.rest.api}'.concat('/')}" + ExchangeMoneyController.ctxController)
public class ExchangeMoneyController {

    @Autowired
    private MoneyExchangeService moneyExchangeService;

    protected static final String ctxController = "exchangemoney";

    @PostMapping
    public ExchangeMoney exchange(@RequestBody @Valid ExchangeMoney exchangeMoney, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return moneyExchangeService.exchangeAmount(exchangeMoney);
        }
        throw new BadRequestException(bindingResult);
    }
}
