package service;

import dao.PicDao;
import dao.TypeDao;
import vo.Type;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeService {
    private TypeDao dao = new TypeDao();

    public void addType(String tname){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String now = format.format(System.currentTimeMillis());

        Type type = new Type();
        type.setTname(tname);
        type.setUpdateDate(now);
        dao.insertType(type);
    }

    public Map<String, Integer> queryAllTypes(int uid){
        List<Type> types = dao.queryAllType();
        Map<String , Integer> maps = new HashMap<>();
        for(Type type : types){
            System.out.println(type.getTname());
            maps.put(type.getTname(), 0);
        }
        PicDao picDao = new PicDao();
        picDao.queryUserPicType(maps, uid);

        return maps;
    }

    public void deleteType(String tname){
        dao.deleteTypeByTname(tname);
    }
}
