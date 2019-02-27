package com.piggymetrics.rates.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.piggymetrics.rates.domain.BaseRequest;
import com.piggymetrics.rates.domain.Currency;

@RestController
public class RatesController {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	BaseRequest request;

	@GetMapping(value = "/latest")
	public String getExchangeRates(Currency base) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json; charset=UTF-8"));
		request.setScur(base.name());
		request.setScur(Currency.EUR.name());
		String requestJson = JSON.toJSONString(request);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://api.k780.com", entity, String.class);

		return responseEntity.getBody();
	}
}
