package by.tut.mdcatalog.week4app.controller.security.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tut.mdcatalog.week4app.controller.constants.PagesConstantsList.ERROR_403_PAGE;

public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

     private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("{} access forbidden: {}.", authentication.getName(), request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath() + ERROR_403_PAGE);
    }
}
