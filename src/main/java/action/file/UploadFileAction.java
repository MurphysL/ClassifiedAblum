package action.file;

import classify.TensorFlowInferenceInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.PicDao;
import org.apache.struts2.ServletActionContext;
import vo.Pic;
import vo.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 垃圾 idea ,出现无论如何也改不了的 BUG 时， 删除 target
// 上传路径。。。
public class UploadFileAction extends ActionSupport {

    private List<File> pic;
    private List<String> picContentType;
    private List<String> picFileName;
    private String root;

    private static final int RESIZE_IMAGE_WIDTH = 64;
    private static final int RESIZE_IMAGE_HEIGHT = 64;

    private static final String INPUT_NODE = "image_batch"; // 输入节点名称
    private static final String OUTPUT_NODE = "final"; // 输出结点名称
    private static final String KEEP_PROB = "keep_prob"; // 下降速率

    public List<File> getPic() {
        return pic;
    }

    public void setPic(List<File> pic) {
        this.pic = pic;
    }

    public List<String> getPicContentType() {
        return picContentType;
    }

    public void setPicContentType(List<String> picContentType) {
        this.picContentType = picContentType;
    }

    public List<String> getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(List<String> picFileName) {
        this.picFileName = picFileName;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    private int classify(File file){
        TensorFlowInferenceInterface tensorflowInterface = new TensorFlowInferenceInterface("H://java/handwriting.pd", "imageType");

        float[] img = getImagePixel(file);

        float[] keep_prob = {1.0f};
        float[] outputs = new float[2];

        tensorflowInterface.feed(INPUT_NODE, img, 1, 64, 64, 1);
        tensorflowInterface.feed(KEEP_PROB, keep_prob, 1, 1);
        tensorflowInterface.run(new String[]{OUTPUT_NODE}, false);
        tensorflowInterface.fetch(OUTPUT_NODE, outputs);

        return (int) outputs[0];
    }

    private float[] getImagePixel(File file){
        float[] floatValues = new float[64 * 64];

        try {
            BufferedImage srcImage = ImageIO.read(file);

            BufferedImage resizeImage = new BufferedImage(RESIZE_IMAGE_WIDTH, RESIZE_IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            resizeImage.getGraphics().drawImage(srcImage.getScaledInstance(RESIZE_IMAGE_WIDTH, RESIZE_IMAGE_HEIGHT, Image.SCALE_SMOOTH),
                    0,
                    0,
                    null);

            int width = resizeImage.getWidth();
            int height = resizeImage.getHeight();
            int minx = resizeImage.getMinX();
            int miny = resizeImage.getMinY();

            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {
                    int value = (resizeImage.getRGB(i, j) & 0xff0000) >> 16;
                    // 数值归一化
                    floatValues[(j * 64) + i] = value * (1f / 255f) - 0.5f;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return floatValues;
    }

    private void uploadPic(String fileName, String path, int lable, int size){
        PicDao dao = new PicDao();
        Pic pic = new Pic();
        User user = (User) ActionContext.getContext().getSession().get("user");
        pic.setUid(user.getUid());
        pic.setTid(1);
        pic.setUsername(user.getUsername());
        pic.setSize(size);
        pic.setPname(fileName);
        pic.setTname("airplane");
        pic.setPath(path);

        dao.insertPic(pic);
    }

    private int copyFile(File origin, String aimPath){
        File file = new File(aimPath);
        int size = 0;
        try {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream fos = null;
            FileInputStream fis = null;

            try{
                fos = new FileOutputStream(aimPath);
                fis = new FileInputStream(origin);
                byte[] b = new byte[1024];
                int len;
                while((len = fis.read(b)) > 0){
                    fos.write(b, 0, len);
                    size += len;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public String execute() throws Exception {
        for(int i = 0;i < pic.size() ;i ++){
            String newName = UUID.randomUUID() + picFileName.get(i).substring(picFileName.get(i).lastIndexOf("."));

            int label = classify(pic.get(0));

            String newDir = "G:\\JavaWorkspace\\JavaEE\\ClassifiedAblum\\src\\main\\webapp\\" + root + label;
            String newAbsolutePath = newDir + "/" +  newName;

            String tomcatDir = "../webapps/ROOT/" + root + label;
            String tomcatAbsolutePath = tomcatDir + "/" + newName;

            String savePath = root +  label + "/" + newName;

            int size = copyFile(pic.get(0), newAbsolutePath);
            copyFile(pic.get(0), tomcatAbsolutePath);

            uploadPic(newName, savePath, label, size);
        }

        return super.execute();
    }
}
