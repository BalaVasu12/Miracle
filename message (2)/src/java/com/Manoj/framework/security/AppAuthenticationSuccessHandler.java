

package com.Manoj.framework.security;

import com.Manoj.framework.utilities.messages.BooleanMessageWithDTO;
import com.source.dto.UserWithPrivilege;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;


public class AppAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    
    @Autowired
    MappingJackson2HttpMessageConverter converter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/json");
        
        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
        UserWithPrivilege empPriv = sessionUser.getModel();
        
        PrintWriter writer = response.getWriter();
        converter.getObjectMapper().writeValue(writer, new BooleanMessageWithDTO(empPriv, true));
        writer.flush();
    }
}
