package action.file;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadFileNumAction extends ActionSupport{

    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String execute() throws Exception {
        ActionContext.getContext().getSession().put("num", num);
        return super.execute();
    }
}
