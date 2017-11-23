package action.login_register;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.UserDao;
import vo.User;

import java.util.regex.Pattern;

public class registerAction extends ActionSupport{
    private String username;
    private String email;
    private String password;
    private String confirm;
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void validate() {
        String regex_email = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        String regex_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$";

        String checkCode = (String) ActionContext.getContext().getSession().get("checkCode");
        System.out.println(email);
        System.out.println(password);
        System.out.println(confirm);
        System.out.println(username);
        System.out.println(code);

        if(email != null && password != null && confirm != null && username != null && code != null &&
                !email.equals("") && !password.equals("") && !confirm.equals("") && !username.equals("") && !code.equals("")){
            if(!Pattern.matches(regex_email, email)){
                addFieldError("email", "邮箱格式不对！");
            }

            if(!Pattern.matches(regex_password, password)){
                addFieldError("password", "密码格式不对！");
            }

            if(!password.equals(confirm)){
                addFieldError("confirm", "密码不一致！");
            }

            if(!code.equals(checkCode)){
                addFieldError("code", "验证码不一致！");
            }
        }else{
            addActionError("必填项不能为空");
        }
    }

    @Override
    public String execute() throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setType("user");

        UserDao dao = new UserDao();
        dao.insertUser(user);

        return super.execute();
    }

}
