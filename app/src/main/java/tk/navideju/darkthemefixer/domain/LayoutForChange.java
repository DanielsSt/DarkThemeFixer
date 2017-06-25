package tk.navideju.darkthemefixer.domain;

import java.util.List;

public class LayoutForChange {
    private String type;
    private String name;
    private List<AttributeForChange> attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
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
}
