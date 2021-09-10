package com.example.jcurrencyapp.data.provider.impl;

import java.time.LocalDate;
import java.util.Optional;

import com.example.jcurrencyapp.data.mode.Currency;
import com.example.jcurrencyapp.data.provider.ProviderInterface;
import com.example.jcurrencyapp.data.provider.controller.WebApiController;
import com.example.jcurrencyapp.data.provider.pojo.nbp.NbpCurrency;
import com.example.jcurrencyapp.data.provider.pojo.nbp.NbpParser;
import com.example.jcurrencyapp.service.ApiResponse;
import com.example.jcurrencyapp.service.NbpWebApiRequest;

public class NbpWebApiJson implements ProviderInterface<Currency> {

	@Override
	public Optional<Currency> getRate(String code, LocalDate date) {
		//Currency rate = Currency.testDataModel();
		
		NbpWebApiRequest request = new NbpWebApiRequest();
		
		ApiResponse response = WebApiController.readApi(request.getSimpleQuery(code, date, false));
		
		if(response.getCode() == 200) {
			NbpCurrency currency = NbpParser.deserialize(response.getText(), false);
			System.out.println("ASK PRICE: " + currency.getRates().get(0).getAsk());
			
			System.out.println(response.getText());
			return Optional.of(Currency.testDataModel());
		}
		
		return Optional.empty();
	}
}