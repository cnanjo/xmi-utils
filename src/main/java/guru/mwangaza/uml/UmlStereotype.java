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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UmlStereotype that = (UmlStereotype) o;

        return taggedValues != null ? taggedValues.equals(that.taggedValues) : that.taggedValues == null;
    }

    @Override
    public int hashCode() {
        int result = basePackageId != null ? basePackageId.hashCode() : 0;
        result = 31 * result + (taggedValues != null ? taggedValues.hashCode() : 0);
        return result;
    }
}
