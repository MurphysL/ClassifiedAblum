package action.ablum;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.PicDao;
import org.apache.struts2.ServletActionContext;
import vo.Pic;
import vo.User;

import java.util.List;

public class CheckAblumAction extends ActionSupport {

    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    private void queryPics() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        PicDao picDao = new PicDao();
        List<Pic> pics = picDao.getPicList(user.getUid(), tname);
        ActionContext.getContext().put("pics", pics);

        String path = ServletActionContext.getServletContext().getRealPath("ablum");
        System.out.println(path);
    }

    @Override
    public String execute() throws Exception {
        queryPics();
        return super.execute();
    }
}
