

package com.Manoj.framework.security;

import com.Manoj.framework.utilities.messages.SimpleErrorMessage;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    @Autowired
    MappingJackson2HttpMessageConverter converter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/json");
        
        PrintWriter writer = response.getWriter();
        converter.getObjectMapper().writeValue(writer, new SimpleErrorMessage("Login attempt failed"));
        writer.flush();
    }
}
