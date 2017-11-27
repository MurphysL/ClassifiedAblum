package action.type;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.PicDao;
import dao.TypeDao;
import service.TypeService;
import vo.Pic;
import vo.User;

import java.io.File;
import java.util.List;

public class DeleteTypeAction extends ActionSupport {

    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public String execute() throws Exception {

        TypeService typeService = new TypeService();
        typeService.deleteType(tname);

        User user = (User) ActionContext.getContext().getSession().get("user");
        PicDao dao = new PicDao();
        List<Pic> picList = dao.queryPicList(user.getUid(), tname);

        for(int i = 0 ;i < picList.size() ;i ++){
            Pic pic = picList.get(i);
            File file = new File(pic.getPath());
            if(file.exists()){
                file.delete();
            }
            dao.deletePicById(pic.getPid());
        }

        TypeService service = new TypeService();
        ActionContext.getContext().getSession().put("types", service.queryAllTypes(user.getUid()));

        return SUCCESS;
    }
}
