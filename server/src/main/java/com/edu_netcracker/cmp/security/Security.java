package com.edu_netcracker.cmp.security;


import com.edu_netcracker.cmp.entities.jpa.UsersJpa;
import com.edu_netcracker.cmp.entities.users.Role;
import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;
    private UsersJpa jpa;


    @Autowired
    public Security(JwtConfigurer configurer, UsersJpa jpa) {
        this.jwtConfigurer = configurer;
        this.jpa = jpa;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("**/sessions/**").hasRole(Role.ADMIN.name())
                .antMatchers("/user").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                .antMatchers("/").permitAll()
                .antMatchers("auth/login").permitAll()
                .and()
                .apply(jwtConfigurer).and().csrf().disable().cors();
    }

    @PostConstruct
    private void initAdmin(){
        if (jpa.findUsersByUserName("admin") == null) {
            User user = new User();
            user.setUserName("admin");
            user.setPassword(passwordEncoder().encode("admin"));
            user.setRole(Role.ADMIN);
            jpa.save(user);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}