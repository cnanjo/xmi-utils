package guru.mwangaza.uml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cnanjo on 10/4/16.
 */
public class UmlStereotypeDefinition extends UmlComponent implements Identifiable, Cloneable {
    private String basePackageId;
    private Map<String, UmlProperty> propertyMap;

    public UmlStereotypeDefinition() {
        propertyMap = new HashMap<>();
    }

    public UmlStereotypeDefinition(String name) {
        this();
        setName(name);
    }

    public Map<String, UmlProperty> getProperties() {
        return propertyMap;
    }

    public void setProperties(Map<String, UmlProperty> properties) {
        this.propertyMap = properties;
    }

    public void addProperty(UmlProperty property) {
        this.propertyMap.put(property.getName(), property);
    }

    public UmlProperty getProperty(String propertyName) {return this.propertyMap.get(propertyName);}
}
