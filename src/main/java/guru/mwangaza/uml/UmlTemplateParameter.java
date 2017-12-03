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

/**
 * Created by cnanjo on 10/3/16.
 */
public class UmlTemplateParameter extends UmlComponent implements Identifiable, Cloneable {
    private UmlClass type;
    private String typeId;
    private String elementId;
    private boolean isParameterReference = true;

    public UmlTemplateParameter() {
    }

    public UmlClass getType() {
        return type;
    }

    public void setType(UmlClass type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public boolean isParameterReference() {
        return isParameterReference;
    }

    public void setParameterReference(boolean parameterReference) {
        isParameterReference = parameterReference;
    }

    public void populateParameterType(UmlModel model) {
        if(typeId != null) {
            type = (UmlClass) model.getIdMap().get(typeId);
        } else {

        }
    }

    public String toString() {
        return getName() + "[id: " + getId() + ", type:" + getType() + ", element id: " + elementId + "]";
    }
}
