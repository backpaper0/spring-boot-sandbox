package com.example.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CompositeUserDetailsService implements UserDetailsManager, InitializingBean {

	@Autowired
	private List<UserDetailsManager> userDetailsManagers;

	@Override
	public void afterPropertiesSet() throws Exception {
		userDetailsManagers = userDetailsManagers.stream()
				.sorted(this::sort)
				.toList();
	}

	private int sort(UserDetailsManager a, UserDetailsManager b) {
		if (a instanceof InMemoryUserDetailsManager) {
			return 1;
		} else if (b instanceof InMemoryUserDetailsManager) {
			return -1;
		}
		return 0;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Iterator<UserDetailsManager> iterator = userDetailsManagers.iterator();
		while (iterator.hasNext()) {
			try {
				return iterator.next().loadUserByUsername(username);
			} catch (UsernameNotFoundException e) {
				if (!iterator.hasNext()) {
					throw e;
				}
			}
		}
		throw new IllegalStateException();
	}

	@Override
	public void createUser(UserDetails user) {
		userDetailsManagers.get(0).createUser(user);
	}

	@Override
	public void updateUser(UserDetails user) {
		userDetailsManagers.get(0).updateUser(user);
	}

	@Override
	public void deleteUser(String username) {
		userDetailsManagers.get(0).deleteUser(username);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		userDetailsManagers.get(0).changePassword(oldPassword, newPassword);
	}

	@Override
	public boolean userExists(String username) {
		for (UserDetailsManager userDetailsManager : userDetailsManagers) {
			if (userDetailsManager.userExists(username)) {
				return true;
			}
		}
		return false;
	}
}
