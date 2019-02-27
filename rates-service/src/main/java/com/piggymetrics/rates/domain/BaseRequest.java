package com.piggymetrics.rates.domain;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BaseRequest {

	private String scur;
	private String tcur;
	private String app = "finance.rate";
	private String appKey = "40422";
	private String sign = "098d994d7e8bed0709f2aa3b4862df5e";
	private String format = "json";

}
