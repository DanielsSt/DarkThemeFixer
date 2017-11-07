package tk.navideju.darkthemefixer.domain;

import java.util.List;

public class AttributeForChange implements ThingForChange {
    private String identifier;
    private String identifierType;
    private Boolean changeTextColor;
    private String newColor;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public Boolean getChangeTextColor() {
        return changeTextColor;
    }

    public void setChangeTextColor(Boolean changeTextColor) {
        this.changeTextColor = changeTextColor;
    }

    public String getNewColor() {
        return newColor;
    }

    public void setNewColor(String newColor) {
        this.newColor = newColor;
    }

    @Override
    public String getName() {
        return this.identifier;
    }

    @Override
    public List getList() {
        return null;
    }

    @Override
    public String getThingType() {
        return "attribute";
    }
}
