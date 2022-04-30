package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.form.AdminForm;
import com.example.model.Account;
import com.example.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping
	public String index(Model model) {
		List<Account> accounts = adminService.findAllAccounts();
		model.addAttribute("accounts", accounts);
		return "admin";
	}

	@GetMapping(params = "username")
	public String detail(@RequestParam String username, Model model,
			@ModelAttribute("adminForm") AdminForm form) {

		Account account = adminService.findAccountById(username);
		model.addAttribute("account", account);

		form.setValidityFrom(account.getValidityFrom());
		form.setValidityTo(account.getValidityTo());

		return "admin_detail";
	}

	@PostMapping(params = { "username", "updateValidity" })
	public String updateValidity(@RequestParam String username, Model model,
			@ModelAttribute("adminForm") @Validated AdminForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {

			Account account = adminService.findAccountById(username);
			model.addAttribute("account", account);

			return "admin_detail";
		}
		adminService.updateValidity(username, form.getValidityFrom(), form.getValidityTo());
		return "redirect:/admin?username=" + username;
	}

	@PostMapping(params = { "username", "unlock" })
	public String unlock(@RequestParam String username) {
		adminService.unlock(username);
		return "redirect:/admin?username=" + username;
	}
}
