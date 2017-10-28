package com.example.vaadinsample;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SpringUI
public class HelloWorld extends UI {

    private final HorizontalLayout content = new HorizontalLayout();
    private final TextField yourName = new TextField();
    private final Button clickMe = new Button("Click me!");
    private final Label message = new Label();

    @Override
    protected void init(final VaadinRequest request) {
        setContent(content);
        yourName.setValue("world");
        content.setMargin(true);
        content.addComponent(yourName);
        content.addComponent(clickMe);
        content.addComponent(message);
        clickMe.addClickListener(event -> {
            message.setValue(String.format("Hello, %1$s!", yourName.getValue()));
        });
    }
}
