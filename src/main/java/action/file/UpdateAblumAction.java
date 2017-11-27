package action.file;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.PicDao;
import service.TypeService;
import vo.User;

import java.util.Map;

public class UpdateAblumAction extends ActionSupport {

    @Override
    public String execute() throws Exception {
        User user = (User) ActionContext.getContext().getSession().get("user");
        TypeService service = new TypeService();
        Map<String, Integer> types =  service.queryAllTypes(user.getUid());
        ActionContext.getContext().getSession().put("types", types);
        return super.execute();
    }
}
