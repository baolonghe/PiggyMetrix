package com.piggymetrics.rates.domain;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true, value = { "date" })
public class ExchangeRatesContainer {

	private Currency base;

	private Map<String, BigDecimal> rates;

	public Currency getBase() {
		return base;
	}

	public void setBase(Currency base) {
		this.base = base;
	}

	public Map<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "RateList{" + "base=" + base + ", rates=" + rates + '}';
	}
}
