package action.file;

import com.opensymphony.xwork2.ActionSupport;
import dao.PicDao;

import java.io.File;

public class DeleteFileAction extends ActionSupport {

    private String path;
    private int pid;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String execute() throws Exception {
        System.out.println(path);
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }


        PicDao dao = new PicDao();
        dao.deletePicById(pid);

        return super.execute();
    }
}
