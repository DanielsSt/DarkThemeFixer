package tk.navideju.darkthemefixer.domain;

import java.util.List;

public interface ThingForChange <T> {
    String getName();
    List<T> getList();
    String getThingType();
}
