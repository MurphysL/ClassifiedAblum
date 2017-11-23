package action.file;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.io.InputStream;

public class DownloadFileAction extends ActionSupport {

    private String inputPath;
    private String contentType;
    private String downFileName;

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDownFileName() {
        return downFileName;
    }

    public void setDownFileName(String downFileName) {
        this.downFileName = downFileName;
    }

    public InputStream getTargetFile(){
        return ServletActionContext.getServletContext().getResourceAsStream(inputPath);
    }
}
