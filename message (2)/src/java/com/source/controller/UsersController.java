package com.source.controller;

import com.Manoj.exceptions.BaseException;
import com.Manoj.exceptions.DataValidationException;
import com.Manoj.framework.AppController;
import com.Manoj.framework.AppDto;
import com.Manoj.framework.search.SearchMap;
import com.Manoj.framework.search.annotations.SearchMapping;
import com.Manoj.framework.utilities.Result;
import com.Manoj.framework.utilities.messages.AppMessage;
import com.Manoj.framework.utilities.messages.BooleanMessage;
import com.Manoj.framework.utilities.messages.BooleanMessageWithDTO;
import com.Manoj.framework.utilities.messages.SimpleErrorMessage;
import com.source.dao.UsersDao;
import com.source.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController extends AppController {

    @RequestMapping("/loginStatus")
    public BooleanMessage isLoggedIn() {
        User loggedInUser = getLoggedInUser();
        if(loggedInUser==null) {
            return new BooleanMessage(false);
        } else {
            AppDto loggedInDetails = getLoggedInDetails();
            return new BooleanMessageWithDTO(loggedInDetails, true);
        }
    }
    
    @RequestMapping(value ="Users/addadminuser")
    public AppMessage addUser(@ModelAttribute("user")User user){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        UsersDao usersDao = new UsersDao(session);
        try{
            usersDao.addUser(user);
            tx.commit();
            return new BooleanMessage(true);
        }catch(Exception ex){
            if(tx!=null){
                tx.rollback();
            }
            Logger.getLogger(UsersController.class.getName()).error(ex);
            return new BooleanMessage(false);
        }finally{
            session.close();
        }
    }
    
    @RequestMapping(value="Users/updateadminuser/{id}")
    public AppMessage updateAminUser(@PathVariable("id")long id,@ModelAttribute("user")User user){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        UsersDao userDao = new UsersDao(session);
        try{
            User existingUser = (User)userDao.read(id);
            userDao.updateAdminUser(user,existingUser);
            String details = "Admin updated";
            long resource = user.getId();
            userDao.saveAuditLogs(user,details,null,resource);
            tx.commit();
            return new BooleanMessage(true);
        }catch(Exception ex){
            if(tx!=null){
                tx.rollback();
            }
           Logger.getLogger(UsersController.class.getName()).error(ex);
           return new BooleanMessage(false);
        }finally{
            session.close();
        }
        
    }
    
    
    @RequestMapping(value = "passwordChange")
    public AppMessage passwordChange(@RequestParam("oldPassword")String oldpassword,@RequestParam("newPassword")String newpassword)throws BaseException{
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        UsersDao userDao = new UsersDao(session);
        try{
            userDao.passwordChangeInLoggedInUser(oldpassword,newpassword);
            tx.commit();
            return new BooleanMessage(true);
        }catch(DataValidationException ex){
            if(tx!=null){
                tx.rollback();
            }
            return new SimpleErrorMessage(ex.getMessage());
        }catch(Exception ex){
            if(tx!=null){
                tx.rollback();
            }
            return new BooleanMessage(false);
        }finally{
            session.close();
        }
    }
    
    @RequestMapping(value={"Users/indexto/admin"})
    public Result doIndex(@SearchMapping SearchMap search)throws BaseException{
        Session session = getSession();
        UsersDao usersDao = new UsersDao(session);
        Result usersList = usersDao.getAdminUsers(search);
        
        return usersList;        
    }
}
