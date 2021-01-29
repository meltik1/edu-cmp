package com.edu_netcracker.cmp.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class MailConfig {

    private String host = "smtp.mailtrap.io";

    private int port = 2525;

    private String username = "username";

    private String password = "password";

}
