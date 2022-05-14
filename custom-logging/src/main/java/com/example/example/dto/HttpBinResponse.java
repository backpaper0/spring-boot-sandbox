package com.example.example.dto;

import java.util.Map;

import lombok.Data;

@Data
public class HttpBinResponse {

	private String data;
	private Map<String, String> headers;
	private String origin;
	private String url;
}
