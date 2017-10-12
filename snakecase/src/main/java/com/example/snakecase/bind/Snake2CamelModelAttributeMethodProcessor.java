package com.example.snakecase.bind;

import javax.servlet.ServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.core.MethodParameter;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

public class Snake2CamelModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {

    public Snake2CamelModelAttributeMethodProcessor() {
        super(false);
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Snake2Camel.class);
    }

    @Override
    protected void bindRequestParameters(final WebDataBinder binder,
            final NativeWebRequest request) {
        super.bindRequestParameters(new DataBinderWrapper(binder), request);
    }

    private static class DataBinderWrapper extends ServletRequestDataBinder {

        public DataBinderWrapper(final DataBinder binder) {
            super(binder.getTarget(), binder.getObjectName());
        }

        @Override
        protected void addBindValues(final MutablePropertyValues mpvs,
                final ServletRequest request) {
            for (final PropertyValue pv : mpvs.getPropertyValues()) {
                mpvs.add(toCamel(pv.getName()), pv.getValue());
            }
        }

        private static String toCamel(final String name) {
            final String[] ss = name.split("_");
            final StringBuilder buf = new StringBuilder();
            buf.append(ss[0].toLowerCase());
            for (int i = 1; i < ss.length; i++) {
                buf
                        .append(ss[i].substring(0, 1).toUpperCase())
                        .append(ss[i].substring(1).toLowerCase());
            }
            return buf.toString();
        }
    }
}
