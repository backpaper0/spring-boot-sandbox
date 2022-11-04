package com.example;

import org.apache.catalina.Context;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.stereotype.Component;

@Component
public class SameSiteCookieTomcatContextCustomizer implements TomcatContextCustomizer {

	@Override
	public void customize(final Context context) {
		final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
		cookieProcessor.setSameSiteCookies("Lax");
		context.setCookieProcessor(cookieProcessor);
	}
}
