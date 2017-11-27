package action.type;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import service.TypeService;
import vo.User;

public class CreateTypeAction extends ActionSupport {

    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public String execute() throws Exception {
        TypeService service = new TypeService();
        service.addType(tname);

        User user = (User) ActionContext.getContext().getSession().get("user");
        ActionContext.getContext().getSession().put("types", service.queryAllTypes(user.getUid()));
        return super.execute();
    }
}
