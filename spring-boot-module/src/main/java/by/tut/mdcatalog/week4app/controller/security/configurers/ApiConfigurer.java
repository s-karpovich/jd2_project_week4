package by.tut.mdcatalog.week4app.controller.security.configurers;

import static by.tut.mdcatalog.week4app.controller.constants.RolesConstantsList.ADMINISTRATOR_ROLE_NAME;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.API_USERS;

import by.tut.mdcatalog.week4app.controller.security.handlers.ApiAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@Order(1)
public class ApiConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiConfigurer(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher(API_USERS)
                .authorizeRequests()
                .anyRequest()
                .hasRole(ADMINISTRATOR_ROLE_NAME)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean("apiAccessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new ApiAccessDeniedHandler();
    }
}
