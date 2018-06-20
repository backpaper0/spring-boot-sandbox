package com.example.globalmethodsecurityexample;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity
public class CustomGlobalMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new CustomMethodSecurityMetadataSource();
    }

    static class CustomMethodSecurityMetadataSource extends AbstractMethodSecurityMetadataSource {

        @Override
        public Collection<ConfigAttribute> getAttributes(final Method method,
                final Class<?> targetClass) {
            final CustomAuthorize ca = AnnotationUtils
                    .findAnnotation(method, CustomAuthorize.class);
            if (ca == null) {
                return null;
            }
            return Arrays.stream(ca.value()).map(this::toConfigAttribute)
                    .collect(Collectors.toSet());
        }

        private ConfigAttribute toConfigAttribute(final Roles r) {
            return () -> "ROLE_" + r.name();
        }

        @Override
        public Collection<ConfigAttribute> getAllConfigAttributes() {
            return null;
        }
    }
}
