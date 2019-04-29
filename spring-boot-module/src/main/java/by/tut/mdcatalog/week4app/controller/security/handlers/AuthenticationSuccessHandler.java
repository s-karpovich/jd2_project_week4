package by.tut.mdcatalog.week4app.controller.security.handlers;

import by.tut.mdcatalog.week4app.controller.exceptions.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static by.tut.mdcatalog.week4app.controller.constants.RolesConstantsList.ADMINISTRATOR_PREFIX;
import static by.tut.mdcatalog.week4app.controller.constants.RolesConstantsList.WITH_PREFIX_CUSTOMER_ROLE_NAME;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ITEMS_PAGE;
import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.USERS_PAGE;

public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException {
        String targetURL = null;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case ADMINISTRATOR_PREFIX:
                    targetURL = USERS_PAGE;
                    break;
                case WITH_PREFIX_CUSTOMER_ROLE_NAME:
                    targetURL = ITEMS_PAGE;
            }
        }
        if (targetURL == null) {
            throw new ControllerException(String.format("Controller error",
                    authentication.getName()));
        }
        if (response.isCommitted()) {
            throw new ControllerException(String.format("Controller error",
                    authentication.getName(), targetURL));
        }
        redirectStrategy.sendRedirect(request, response, targetURL);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
