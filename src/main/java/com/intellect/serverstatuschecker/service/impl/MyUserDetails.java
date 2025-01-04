package com.intellect.serverstatuschecker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.intellect.serverstatuschecker.domain.Users;
import com.intellect.serverstatuschecker.domain.UsersPrincipal;
import com.intellect.serverstatuschecker.exception.ServerDetailsBusinessException;
import com.intellect.serverstatuschecker.repository.UserRepository;
import com.intellect.serverstatuschecker.util.ApplicationConstants;

@Service
public class MyUserDetails implements UserDetailsService {
	
    public MyUserDetails() {
		super();
	}

	@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            // Fetch user from the repository
            Users users = userRepository.findByEmail(email);
            
            if (users == null) {
                // If user is not found, throw your custom exception
                throw new ServerDetailsBusinessException(ApplicationConstants.USER_NAME_NOT_FOUND);
            }
            
            // Return UserPrincipal object if user is found
            return new UsersPrincipal(users);

        } catch (ServerDetailsBusinessException ex) {
            // Catch custom exception and wrap it in UsernameNotFoundException
            throw new UsernameNotFoundException(ApplicationConstants.USER_NAME_NOT_FOUND, ex);
        }
    }
}
