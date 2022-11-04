package com.example.globalmethodsecurityexample;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity
public class CustomGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	@Override
	protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
		return new SecuredAnnotationSecurityMetadataSource(new CustomAnnotationMetadataExtractor());
	}

	static class CustomAnnotationMetadataExtractor
			implements AnnotationMetadataExtractor<CustomAuthorize> {

		@Override
		public Collection<? extends ConfigAttribute> extractAttributes(
				final CustomAuthorize securityAnnotation) {
			return Arrays.stream(securityAnnotation.value())
					.map(this::toConfigAttribute)
					.collect(Collectors.toSet());
		}

		private ConfigAttribute toConfigAttribute(final Roles r) {
			return () -> "ROLE_" + r.name();
		}
	}
}
