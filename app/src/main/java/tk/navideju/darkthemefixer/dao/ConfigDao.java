package tk.navideju.darkthemefixer.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import de.robv.android.xposed.XposedBridge;
import tk.navideju.darkthemefixer.domain.PackageForChange;

public class ConfigDao {

    private List<PackageForChange> packages = null;

    public ConfigDao(){
        InputStream inputStream = null;
        inputStream = ConfigDao.class.getClassLoader().getResourceAsStream("assets/config.json");

        Gson g = new Gson();

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
        } catch (IOException e) {
            XposedBridge.log(e.toString());
        }

        String jsonStr = "";

        try {
            jsonStr = result.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            XposedBridge.log(e.toString());
            jsonStr = "[]";
        }

        Type listType = new TypeToken<List<PackageForChange>>() {}.getType();
        packages = g.fromJson(jsonStr, listType);
    }

    public List<PackageForChange> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageForChange> packages) {
        this.packages = packages;
    }

    public PackageForChange getByName(String name){
        for(PackageForChange pkg : packages) {
            if(name.equals(pkg.getName())){
                return pkg;
            }
        }
        return null;
    }


}
