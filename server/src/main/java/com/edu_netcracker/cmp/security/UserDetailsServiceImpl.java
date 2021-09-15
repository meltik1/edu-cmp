package com.edu_netcracker.cmp.security;

import com.edu_netcracker.cmp.entities.users.User;
import com.edu_netcracker.cmp.entities.jpa.UsersJpa;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersJpa usersJpa;

    public UserDetailsServiceImpl(UsersJpa usersJpa) {
        this.usersJpa = usersJpa;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = usersJpa.findUsersByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exists");
        }
        return SecurityUser.fromUser(user);
    }
}
