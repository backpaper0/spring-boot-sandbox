package com.example.locale;

import java.util.Locale;
import java.util.SimpleTimeZone;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleTimeZoneAwareLocaleContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleContextResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component(DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME)
public class ItalyOnlyLocaleContextResolver implements LocaleContextResolver {

	@Override
	public Locale resolveLocale(final HttpServletRequest request) {
		return Locale.ITALY;
	}

	@Override
	public void setLocale(final HttpServletRequest request, final HttpServletResponse response,
			final Locale locale) {
	}

	@Override
	public LocaleContext resolveLocaleContext(final HttpServletRequest request) {
		return new SimpleTimeZoneAwareLocaleContext(Locale.ITALY, new SimpleTimeZone(1, "CET"));
	}

	@Override
	public void setLocaleContext(final HttpServletRequest request,
			final HttpServletResponse response, final LocaleContext localeContext) {
	}
}
