package tk.navideju.darkthemefixer;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
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

    @Override
    public void handleInitPackageResources(final XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {
        final PackageForChange pkg = configDao.getByName(initPackageResourcesParam.packageName);
        if(pkg != null){
            XposedBridge.log(LOG_PRE + "Adding fixes for " + initPackageResourcesParam.packageName);
            for(final LayoutForChange layout : pkg.getLayouts()){
                initPackageResourcesParam.res.hookLayout(initPackageResourcesParam.packageName, layout.getType(), layout.getName(), new XC_LayoutInflated() {
                    @Override
                    public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                        XposedBridge.log(LOG_PRE + "Adding fixes for " + initPackageResourcesParam.packageName + " layout: " + layout.getName());
                        for(AttributeForChange attr : layout.getAttributes()) {
                            TextView tv = null;
                            View v = null;
                            int newColor = Color.parseColor(attr.getNewColor());

                            if(attr.getChangeTextColor()){
                                tv = (TextView) liparam.view.findViewById(
                                        liparam.res.getIdentifier(attr.getIdentifier(), attr.getIdentifierType(), initPackageResourcesParam.packageName));
                                tv.setTextColor(newColor);
                            }else{
                                v = (View) liparam.view.findViewById(
                                        liparam.res.getIdentifier(attr.getIdentifier(), attr.getIdentifierType(), initPackageResourcesParam.packageName));
                                v.setBackgroundColor(newColor);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }
}
