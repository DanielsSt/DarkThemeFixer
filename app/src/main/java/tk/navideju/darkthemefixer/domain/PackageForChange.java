package tk.navideju.darkthemefixer.domain;

import java.util.List;

public class PackageForChange {
    private String name;
    private List<LayoutForChange> layouts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LayoutForChange> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<LayoutForChange> layouts) {
        this.layouts = layouts;
    }
}
