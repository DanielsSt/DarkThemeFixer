package tk.navideju.darkthemefixer;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import tk.navideju.darkthemefixer.dao.ConfigDao;
import tk.navideju.darkthemefixer.domain.AttributeForChange;
import tk.navideju.darkthemefixer.domain.LayoutForChange;
import tk.navideju.darkthemefixer.domain.PackageForChange;

public class Main implements IXposedHookZygoteInit, IXposedHookInitPackageResources {

    private static final String LOG_PRE = "[DarkThemeFixer] ";
    private String MODULE_PATH;
    private List<PackageForChange> packages = null;
    private ConfigDao configDao = new ConfigDao();
    private static XSharedPreferences pref = null;

    @Override
    public void handleInitPackageResources(final XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {
        pref = new XSharedPreferences("tk.navideju.darkthemefixer","fix_states");
        pref.makeWorldReadable();
        final PackageForChange pkg = configDao.getByName(initPackageResourcesParam.packageName);
        if(pkg != null){
            for(final LayoutForChange layout : pkg.getLayouts()){
                initPackageResourcesParam.res.hookLayout(initPackageResourcesParam.packageName, layout.getType(), layout.getName(), new XC_LayoutInflated() {
                    @Override
                    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                        for(AttributeForChange attr : layout.getAttributes()) {
                            TextView tv = null;
                            View v = null;
                            int newColor = Color.parseColor(attr.getNewColor());
                            int resId = liparam.res.getIdentifier(attr.getIdentifier(), attr.getIdentifierType(), initPackageResourcesParam.packageName);

                            if(resId > 0
                                    && pref.getBoolean(initPackageResourcesParam.packageName+"."+layout.getName()+"."+attr.getIdentifier(), true)) {
                                if (attr.getChangeTextColor()) {
                                    tv = (TextView) liparam.view.findViewById(resId);
                                    tv.setTextColor(newColor);
                                } else {
                                    v = (View) liparam.view.findViewById(resId);
                                    v.setBackgroundColor(newColor);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        pref = new XSharedPreferences("tk.navideju.darkthemefixer","fix_states");
        pref.makeWorldReadable();
        File test = pref.getFile();
        Boolean a = pref.getBoolean("com.android.chrome.toolbar_phone.location_bar",true);
        XposedBridge.log(LOG_PRE+"From zygote: "+a+" can read?: "+test.canRead());
        MODULE_PATH = startupParam.modulePath;
    }
}
