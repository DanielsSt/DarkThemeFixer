package tk.navideju.darkthemefixer.domain;

import java.util.List;

public class PackageForChange implements ThingForChange {
    private String name;
    private List<LayoutForChange> layouts;
    private String description;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<LayoutForChange> getList() {
        return this.layouts;
    }

    @Override
    public String getThingType() {
        return "package";
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

    public LayoutForChange getByName(String name){
        for(LayoutForChange layout : layouts) {
            if(name.equals(layout.getName())){
                return layout;
            }
        }
        return null;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
