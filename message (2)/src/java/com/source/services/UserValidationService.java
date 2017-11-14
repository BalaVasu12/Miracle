

package com.source.services;

import com.Manoj.framework.security.SessionUser;
import com.Manoj.framework.utilities.SFM;
import com.source.dao.UsersDao;
import com.source.dto.UserWithPrivilege;
import com.source.entities.User;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService implements UserDetailsService{

     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Logger.getLogger(this.getClass()).debug("Loading by username");
        Session session = SFM.openSession();
        UsersDao userDao = new UsersDao(session);
        User users = userDao.findByUsername(username);
        
        if(users!=null) {
            Set<String> authoritiesSet = userDao.getRealPrivilegeList(users);
            List authorities = new LinkedList<GrantedAuthority>();
            
            for(String rolePrivilege: authoritiesSet) {
                authorities.add(new GrantedAuthorityImpl(rolePrivilege));
            }
            authorities.add(new GrantedAuthorityImpl("LOGGEDIN")); //Standard for all users
            
            UserWithPrivilege userWithPriv = new UserWithPrivilege(users, authoritiesSet);
            UserDetails user = new SessionUser(users.getUserName(), users.getPassword(), users.isIsActive(), true, true, true, userWithPriv, authorities);
            return user;
        } else
            throw new UsernameNotFoundException("Invalid account");
    }

    class GrantedAuthorityImpl implements GrantedAuthority {

        String authority;
        
        public GrantedAuthorityImpl(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
        
    }
}
