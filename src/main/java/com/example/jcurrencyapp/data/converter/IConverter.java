package com.example.jcurrencyapp.data.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.example.jcurrencyapp.exceptions.ConverterException;

//Returns currency rate selected by code and value
public interface IConverter {
	public BigDecimal getRate(String data);
}