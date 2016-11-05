package guru.mwangaza.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnanjo on 10/4/16.
 */
public class UmlStereotype extends UmlComponent implements Identifiable, Cloneable {
    private String basePackageId;
    private List<TaggedValue> taggedValues;

    public UmlStereotype() {
        taggedValues = new ArrayList<>();
    }

    public String getBasePackageId() {
        return basePackageId;
    }

    public void setBasePackageId(String basePackageId) {
        this.basePackageId = basePackageId;
    }

    public List<TaggedValue> getTaggedValues() {
        return taggedValues;
    }

    public void setTaggedValues(List<TaggedValue> taggedValues) {
        this.taggedValues = taggedValues;
    }

    public void addTaggedValue(TaggedValue taggedValue) {
        this.taggedValues.add(taggedValue);
    }

    public String getTaggedValue(String key) {
        for(TaggedValue tag : taggedValues) {
            if(tag.getKey().equalsIgnoreCase(key)) {
                return tag.getValue();
            }
        }
        return null;
    }
}
