package tk.navideju.darkthemefixer.domain;

import java.util.List;

public class LayoutForChange implements ThingForChange {
    private String type;
    private String name;
    private List<AttributeForChange> attributes;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<AttributeForChange> getList() {
        return this.attributes;
    }

    @Override
    public String getThingType() {
        return "layout";
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttributeForChange> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeForChange> attributes) {
        this.attributes = attributes;
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
