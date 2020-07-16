package com.example;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExampleController {

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/protected")
	public ModelAndView protectedPage(@AuthenticationPrincipal final OidcUser user) {
		final List<Entry<String, Object>> claims = user.getClaims().entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toList());
		final Map<String, ?> model = Map.of("claims", claims);
		return new ModelAndView("protected", model);
	}
}
