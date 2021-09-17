package com.example.jcurrencyapp.data.provider.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.testng.annotations.Test;

public class NbpXmlProviderTest {

	@Test
	public void getDataTest_GivenNbpJsonProvider_WhenCall_ShouldGiveValidResponse() {
		String validResponse = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>C</Table><Currency>dolar amerykański</Currency><Code>USD</Code><Rates><Rate><No>070/C/NBP/2016</No><EffectiveDate>2016-04-12</EffectiveDate><Bid>3.6949</Bid><Ask>3.7695</Ask></Rate></Rates></ExchangeRatesSeries>";
		NbpXmlProvider provider = new NbpXmlProvider();
		
		assertThat(provider.getData("USD", LocalDate.of(2016, 4, 12))).isEqualTo(validResponse);
	}
}