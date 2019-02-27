package com.piggymetrics.rates.controller;

import org.springframework.web.client.RestTemplate;

public class RatesController {

	@Autowired
	RestTemplate restTemplate;
	
	public JSONObject getExchangeRates(Currency currency) {
		
	   JSONObject res=restTemplate.getForObject("http://api.k780.com/",httpEnty, String.class);
	   
	   return res;
	}
}
