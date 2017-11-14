package com.source.controller;

import com.Manoj.exceptions.BadRequestException;
import com.Manoj.exceptions.DataValidationException;
import com.Manoj.exceptions.DatabaseException;
import com.Manoj.framework.AppController;
import com.Manoj.framework.utilities.messages.AppMessage;
import com.Manoj.framework.utilities.messages.BooleanMessage;
import com.Manoj.framework.utilities.messages.BooleanMessageWithDetails;
import com.Manoj.framework.utilities.messages.SimpleErrorMessage;
import com.source.dao.UsersDao;
import com.source.entities.User;
import com.source.exceptions.StorageSystemException;
import com.source.utilities.CommonUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static org.apache.logging.log4j.web.WebLoggerContextUtils.getServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("miracle")
public class CommonController extends AppController{
    private static final String SAVE_DIR = "com.img";
    private final String UPLOAD_DIRECTORY = "com.img";
    @RequestMapping(value = "createUser")
    public AppMessage createUser(HttpServletRequest request , HttpServletResponse response) throws Exception{
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        UsersDao usersDao = new UsersDao(session);
        try{
            User user = usersDao.createUser(request);
            return new BooleanMessageWithDetails(user, true);
        }catch(DataValidationException ex){
            if(tx!=null){
                tx.rollback();
            }
            return  new BooleanMessage(false);
        }
    }
   
    @RequestMapping(value = "profileUpload",method = RequestMethod.POST)     
    public BooleanMessage imageUploadHandler(HttpServletRequest request ,MultipartFile multipartFile, HttpServletResponse response) throws DatabaseException, BadRequestException, StorageSystemException, IOException, IllegalStateException, ServletException, JSONException {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        UsersDao usersDao = new UsersDao(session);
        String jsonData = usersDao.jSonParsing(request);
         JSONObject jsonObject = new JSONObject(jsonData);
         
         String profilePath = jsonObject.getString("path");
         String userId = jsonObject.getString("userId");
         User user = (User)usersDao.read(Long.valueOf(userId));
        // ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
       String appPath = request.getServletContext().getRealPath(profilePath);
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
         
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            // refines the fileName in case it is an absolute path
            fileName = new File(fileName).getName();
            part.write(savePath + File.separator + fileName);
        }
        request.setAttribute("message", "Upload has been done successfully!");
        getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
        return new BooleanMessage(true);
    }
    
     private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
     
    @RequestMapping(value = "profileupload",method = RequestMethod.POST)
    public void profileUpload(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException, JSONException, DatabaseException{
        Session session = getSession();
        UsersDao usersDao = new UsersDao(session);
        String jsonData = usersDao.jSonParsing(request);
         JSONObject jsonObject = new JSONObject(jsonData);
         
         String profilePath = jsonObject.getString("path");
         String userId = jsonObject.getString("userId");
        try {
            String str = "SomeMoreTextIsHere";
            File newTextFile = new File("profilePath");

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            System.out.println(fw);
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
     
    }
     
    @RequestMapping(value = "imageUpload/{userId}",headers = "content-type=multipart/*",method = RequestMethod.POST)    
    public AppMessage imageUploadHandler(@PathVariable("userId")long userId,@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws DatabaseException, BadRequestException, StorageSystemException {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        // ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        UsersDao usersDao = new UsersDao(session);
        User user = (User)usersDao.read(userId);
        
        if (!multipartFile.isEmpty()) {
            String basePath = CommonUtils.getStorageFolder("Profile", 1+"");
            try {
                CommonUtils.deleteAllFilesInFolder(basePath);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(basePath + multipartFile.getOriginalFilename())));
                FileCopyUtils.copy(multipartFile.getInputStream(), stream);
                String filepath = multipartFile.getOriginalFilename();
                user.setProfile(basePath+filepath);
                System.out.println(filepath);
                stream.close();
                
                usersDao.update(user);
                
            } catch (Exception e) {
                return new SimpleErrorMessage("There was an error in upload => " + e.getMessage());
            }
        } else {
            return new SimpleErrorMessage("No file was uploade");
        }
        
        tx.commit();
        session.close();
        return new BooleanMessageWithDetails(user,true);
    }
}
