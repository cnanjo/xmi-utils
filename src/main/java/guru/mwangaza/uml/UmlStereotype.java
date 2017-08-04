package guru.mwangaza.uml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cnanjo on 10/4/16.
 */
public class UmlStereotype extends UmlComponent implements Identifiable, Cloneable {
    private String basePackageId;
    private Map<String, TaggedValue> taggedValues;

    public UmlStereotype() {
        taggedValues = new HashMap<>();
    }

    public String getBasePackageId() {
        return basePackageId;
    }

    public void setBasePackageId(String basePackageId) {
        this.basePackageId = basePackageId;
    }

    public Map<String, TaggedValue> getTaggedValues() {
        return taggedValues;
    }

    public void setTaggedValues(Map<String, TaggedValue> taggedValues) {
        this.taggedValues = taggedValues;
    }

    public void addTaggedValue(TaggedValue taggedValue) {
        this.taggedValues.put(taggedValue.getKey(), taggedValue);
    }

    public TaggedValue getTaggedValue(String key) {return this.taggedValues.get(key);}
}
