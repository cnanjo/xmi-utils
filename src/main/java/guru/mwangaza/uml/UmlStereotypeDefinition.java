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
