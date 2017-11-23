package action.file;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.PicDao;
import vo.User;

import java.util.Map;

public class UpdateAblumAction extends ActionSupport {

    @Override
    public String execute() throws Exception {
        User user = (User) ActionContext.getContext().getSession().get("user");
        PicDao picDao = new PicDao();
        Map<String, Integer> types =  picDao.queryUserPicType(user.getUid());
        ActionContext.getContext().getSession().put("types", types);
        return super.execute();
    }
}
