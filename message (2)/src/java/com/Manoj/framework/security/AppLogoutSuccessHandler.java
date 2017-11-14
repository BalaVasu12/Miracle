

package com.Manoj.framework.security;

import com.Manoj.framework.utilities.messages.BooleanMessage;
import com.Manoj.framework.utilities.messages.BooleanMessageWithDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


public class AppLogoutSuccessHandler implements LogoutSuccessHandler{
    
    @Autowired
    MappingJackson2HttpMessageConverter converter;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/json");
        
        PrintWriter writer = response.getWriter();
        converter.getObjectMapper().writeValue(writer, new BooleanMessage(true));
        writer.flush();
    }
}
