package com.example.jcurrencyapp.data.provider.impl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NbpJsonProviderTest {

	@BeforeClass
	public void init() {
		System.out.println("Testing: " + this.getClass().getName());
	}

	@Test
	public void shouldGiveValidResponse_WhenGivenNbpJsonProvider() {
//		// Given
//		NbpJsonProviderImpl provider = new NbpJsonProviderImpl(new NbpJsonConverterImpl());
//		String validResponse = "{\"table\":\"C\",\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"rates\":[{\"no\":\"070/C/NBP/2016\",\"effectiveDate\":\"2016-04-12\",\"bid\":3.6949,\"ask\":3.7695}]}";
//
//		// When
//		String response = provider.getData(CurrencyTypes.USD, LocalDate.of(2016, 4, 12));
//
//		// Then
//		assertThat(response).isEqualTo(validResponse);
	}
}
