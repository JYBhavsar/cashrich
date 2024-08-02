package com.example.util;

import org.springframework.http.HttpHeaders;

public class ApiUtils {

	public static HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-CMC_PRO_API_KEY", "27ab17d1-215f-49e5-9ca4-afd48810c149");
		return headers;
	}
}
