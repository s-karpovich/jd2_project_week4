package by.tut.mdcatalog.week4app.controller.security.configurers;

import static by.tut.mdcatalog.week4app.controller.constants.RolesConstantsList.ADMINISTRATOR_ROLE_NAME;
import static by.tut.mdcatalog.week4app.controller.constants.RolesConstantsList.CUSTOMER_ROLE_NAME;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ABOUTUS_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ADD_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ERROR_403_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.HOME_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ITEMS_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.LOGIN_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.LOGOUT_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.USERS_PAGE;

import by.tut.mdcatalog.week4app.controller.security.handlers.AccessDeniedHandler;
import by.tut.mdcatalog.week4app.controller.security.handlers.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebConfigurer(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ITEMS_PAGE)
                .hasRole(CUSTOMER_ROLE_NAME)
                .antMatchers(USERS_PAGE, ADD_PAGE)
                .hasRole(ADMINISTRATOR_ROLE_NAME)
                .antMatchers(HOME_PAGE, LOGIN_PAGE, LOGOUT_PAGE, ABOUTUS_PAGE, ERROR_403_PAGE)
                .permitAll()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler();
    }

    @Bean("appAccessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
