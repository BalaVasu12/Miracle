package com.source.dao;

import com.Manoj.exceptions.BaseException;
import com.Manoj.exceptions.DataValidationException;
import com.Manoj.exceptions.DatabaseException;
import com.Manoj.framework.AppDao;
import com.Manoj.framework.AppModel;
import com.Manoj.framework.search.SearchMap;
import com.Manoj.framework.search.SearchOnCriteriaInterface;
import com.Manoj.framework.utilities.Result;
import com.Manoj.framework.utilities.ResultJsonViewList;
import com.monitorjbl.json.Match;
import com.source.config.Roles;
import com.source.entities.RolePrivileges;
import com.source.entities.User;
import com.source.entities.UserRoles;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;

public class UsersDao extends AppDao{

    public UsersDao(){
        super();
    }
    
    public UsersDao(Session session){
        super(session);
    }

    @Override
    public AppModel getModelInstance() {
        return new User();
    }

    @Override
    protected Class<?> getModelClass() {
        return User.class;
    }
    public User findByUsername(String username) {
        Session session = getDaoSession();
        Criteria crit = session.createCriteria(getModelClass());
        crit.add(Restrictions.eq("userName", username));
        return (User) crit.uniqueResult();
    }
     
     public Set<String> getRealPrivilegeList(User users) {
        Set<String> privList = new HashSet<>();
//        for (UserRoles userRole : users.getRoles()) {
//            for (RolePrivileges privilege : userRole.getRoles().getPrivileges()) {
//                privList.add(privilege.getPrivilegeName());
//            }
//        }

        return privList;
    }
     @Override
    public long add(AppModel model, Session session) throws DatabaseException {
        User User = (User) model;
        if(User.getUserName()== null)
            throw new DatabaseException("Username is mandatory");
        
        User existing  = findByUsername(User.getUserName());
        if(existing != null) {
            throw new DatabaseException("This username is already used for another employee: "+existing.getUserName()+" ("+existing.getId()+")");
        }
        
        return super.add(model, session);
    }
    
     @Override
    public void update(AppModel updatedModel, Session session) throws DatabaseException {
        User user = (User) updatedModel;
        if(user.getUserName()== null)
            throw new DatabaseException("Username is mandatory");
        
        User existing  = findByUsername(user.getUserName());
        if(existing != null && user.getId()!=existing.getId()) {
            throw new DatabaseException("This username is already used for another employee: "+existing.getUserName()+" ("+existing.getId()+")");
        }
        
        super.update(updatedModel, session);
    }
    
    public void addUser(User user)throws BaseException{
        Session session = getDaoSession();
        RolesDao rolesDao = new RolesDao(session);
        user.setRole(Roles.ADMIN);
        user.setCreatedOn(Calendar.getInstance().getTime());
//        user.setLastmodifiedby((User) getOwner());
        
//        com.source.entities.Roles role = rolesDao.findByName(Roles.ADMIN);
//        if (role != null) {
//            List<UserRoles> rolesArray = new ArrayList<>();
//            UserRoles userRole = new UserRoles();
//            userRole.setRoles(role);
//            userRole.setUser(user);
//            rolesArray.add(userRole);
////            user.setRoles(rolesArray);
//        }
//        
        
        try{
            add(user);
        }catch(Exception ex){
            ex.printStackTrace();
            throw  ex;
        }
    }

    public void updateAdminUser(User user,User existingUser)throws BaseException{
        Session session = getDaoSession();
//        user.setLastmodifiedby((User)getOwner());
//        user.setLastModifiedOn(Calendar.getInstance().getTime());
        user.setUserName(existingUser.getUserName());
        
//        user.setRoles(existingUser.getRoles());
//        if(user.getPassword()==null || user.getPassword().equals("")) {
//            user.setPassword(existingUser.getPassword());
//        }
//        
//        if(user.getRoles()==null)
//            user.setRoles(new ArrayList<UserRoles>());
//        
//        user.getRoles().addAll(existingUser.getRoles());
        
        
        
        try{
            update(user);
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    public void addClientAdmin(User user)throws BaseException{
        Session session = getDaoSession();
        UsersDao userDao = new UsersDao(session);
        RolesDao rolesDao = new RolesDao(session);
        
        user.setRole(Roles.ADMIN);
        user.setCreatedOn(Calendar.getInstance().getTime());
//        user.setLastmodifiedby((User)getOwner());
//        com.source.entities.Roles role = rolesDao.findByName(Roles.ADMINCLIENT);
//        if (role != null) {
//            List<UserRoles> rolesArray = new ArrayList<>();
//            UserRoles userRole = new UserRoles();
//            userRole.setRoles(role);
//            userRole.setUser(user);
//            rolesArray.add(userRole);
//            user.setRoles(rolesArray);
//        }
        try{
            add(user);
        }catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    public void updateadminClient(User user,User existingUser) throws BaseException{
        Session session = getDaoSession();
        user.setCreatedOn(existingUser.getCreatedOn());
//        user.setLastModifiedOn(Calendar.getInstance().getTime());
//        user.setLastmodifiedby((User)getOwner());
        
//        user.setRoles(existingUser.getRoles());
//        user.setUserName(existingUser.getUserName());
//        
//        if(user.getRoles()==null)
//            user.setRoles(new ArrayList<UserRoles>());
//        
//        user.getRoles().addAll(existingUser.getRoles());
        
         if(user.getPassword()==null || user.getPassword().equals("")){
            user.setPassword(existingUser.getPassword());
        }  
        
        try{
            update(user);
        }catch(Exception ex){
            ex.printStackTrace();
            throw  ex;
        }
    }
    
    public void passwordChangeInLoggedInUser(String oldpassword,String newpassword)throws BaseException{
        Session session = getDaoSession();
        UsersDao  userDao = new UsersDao(session);
        User user =(User)getOwner();
        
        if(!user.getPassword().equals(oldpassword)){
            
           throw new DataValidationException("The old password is incorrect"); 
        }
        
        if(!oldpassword.equals(newpassword)){
            user.setPassword(newpassword);
        }else{
            throw new DataValidationException("The new password must not be same as old password");
        }
        try{
            userDao.update(user);
        }catch(Exception ex){
            throw  ex;
        }
    }
    
    public User getUserDetail(String userName) {
        Session session = getDaoSession();
        Criteria userCrit = session.createCriteria(getModelClass());
        userCrit.add(Restrictions.eq("userName", userName));
        userCrit.add(Restrictions.eq("isActive", true));
        
        return (User) userCrit.uniqueResult();
    } 
    
    public Result getAdminUsers(SearchMap search) throws DatabaseException {
        Session session = getDaoSession();
        Criteria indexCrit;
        SearchOnCriteriaInterface searchService = searchService();
        ResultJsonViewList result;
        
        try {
            indexCrit = session.createCriteria(getModelClass());
            indexCrit.add(Restrictions.eq("role", Roles.ADMIN ));
            
            result = new ResultJsonViewList(
                    searchService.searchOrderAndPaginate(getModelClass(), indexCrit, search)
            );

            return result;
        } catch (Exception ex) {
            throw new DatabaseException("SQL Error wile listing", ex);
        } finally {

        }
    }
    
    public Result getClientUsers(SearchMap search) throws DatabaseException {
        Session session = getDaoSession();
        Criteria indexCrit;
        SearchOnCriteriaInterface searchService = searchService();
        ResultJsonViewList result;
        
        try {
            indexCrit = session.createCriteria(getModelClass());
            indexCrit.add(Restrictions.eq("role", Roles.ADMINCLIENT ));
            
            result = new ResultJsonViewList(
                    searchService.searchOrderAndPaginate(getModelClass(), indexCrit, search)
            );

            return result;
        } catch (Exception ex) {
            throw new DatabaseException("SQL Error wile listing", ex);
        } finally {

        }
    }

    public User createUser(HttpServletRequest request) throws Exception {
       Session session = getDaoSession();
       UsersDao usersDao = new UsersDao(session);
       
       String name;
       String passWord;
       String userName;
       String eMail;
       String mobileNumber;
       String address;
       String role;
       User user;
        
       try{
           String jsonData = usersDao.jSonParsing(request);
           JSONObject jSONObject = new JSONObject(jsonData);
           
           name = jSONObject.getString("name");
           passWord = jSONObject.getString("password");
           userName = jSONObject.getString("username");
           mobileNumber = jSONObject.getString("mobilenumber");
           address = jSONObject.getString("address");
           role = jSONObject.getString("role");
           eMail = jSONObject.getString("email");
           
           User mobileNumbers = getMobileNumber(mobileNumber); 
           if(mobileNumbers!=null){
               throw new DataValidationException("Mobile Number Already Heaving");
           }else{
               user = new User();
               user.setCreatedOn(Calendar.getInstance().getTime());
               user.setAddress(address);
               user.setEmail(eMail);
               user.setIsActive(true);
               user.setMobilenumber(mobileNumber);
               user.setName(name);
               user.setPassword(passWord);
               user.setProfile(null);
               user.setRole(role);
               user.setUserName(userName);               
           }
           usersDao.add(user);
       }catch(Exception ex){
        ex.printStackTrace(System.out);
        throw ex;
       }
       
       return user;
       
    }

    public User getMobileNumber(String mobileNumber) {
        Session session = getDaoSession();
        Criteria crit = session.createCriteria(getModelClass());
        crit.add(Restrictions.eq("mobilenumber", mobileNumber));
        
        return (User) crit.uniqueResult();
    }
}

