package vo;

public class Pic {
    private int pid;
    private int tid;
    private int uid;
    private String username;
    private String tname;
    private String pname;
    private int size;
    private String uploadDate;
    private String path;


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

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "pid=" + pid +
                ", tid=" + tid +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", tname='" + tname + '\'' +
                ", pname='" + pname + '\'' +
                ", size=" + size +
                ", uploadDate='" + uploadDate + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
