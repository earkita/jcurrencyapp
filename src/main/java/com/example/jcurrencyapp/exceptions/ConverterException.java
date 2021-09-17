package com.example.jcurrencyapp.exceptions;

public class ConverterException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ConverterException(String msg) {
		super("ConverterException: [" + msg + "]");
	}
}
