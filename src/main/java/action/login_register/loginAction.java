package action.login_register;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.PicDao;
import dao.UserDao;
import vo.Pic;
import vo.User;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class loginAction extends ActionSupport{

    private static final String USER = "user";
    private static final String ADMIN = "admin";

    private String email;
    private String password;
    private String type;

    private User user;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void validate() {
        String regex_email = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

        if(email != null && password != null && !email.equals("") && !password.equals("")){
            if(!Pattern.matches(regex_email, email)){
                addFieldError("email", "邮箱格式错误！");
            }

            UserDao userDao = new UserDao();
            user = userDao.selectUser(email);
            if(user == null || user.getPassword() == null || !user.getPassword().equals(password) || !type.equals(user.getType())){
                addFieldError("password", "密码不正确！");
            }
        }else{
            addActionError("必填项不能为空");
        }
    }

    private void queryUserTypes(){
        PicDao picDao = new PicDao();
        Map<String, Integer> types =  picDao.queryUserPicType(user.getUid());
        ActionContext.getContext().getSession().put("types", types);
    }

    private void queryAllUser(){
        UserDao userDao = new UserDao();
        List<User> users = userDao.selectUserList();
        ActionContext.getContext().getSession().put("users", users);
    }

    @Override
    public String execute() throws Exception {
        ActionContext.getContext().getSession().put("user", user);
        if(user.getType().equals(USER)){
            queryUserTypes();
            return "userSuccess";
        }else{
            queryAllUser();
            return "adminSuccess";
        }
    }
}
