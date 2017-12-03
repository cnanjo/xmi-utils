/**
 * Copyright (C) 2013 - 2017 Claude Nanjo
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by cnanjo.
 */
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
