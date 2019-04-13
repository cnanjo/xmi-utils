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

import guru.mwangaza.core.utils.CompositeIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a UML Class
 * 
 * @author cnanjo
 *
 */
public class UmlClass extends BaseClassifier {

	private boolean isUmlPrimitive = false;

	private List<UmlInterface> realizations = new ArrayList<>();
	private List<String> realizationIds = new ArrayList<>();
	
	public UmlClass() {
		super();
	}
	
	public UmlClass(String name) {
		this();
		setName(name);
	}

	public UmlClass(String name, boolean isUmlPrimitive) {
		this();
		setName(name);
		setUmlPrimitive(isUmlPrimitive);
		if(isUmlPrimitive) {
			setPrimitive(true);
		}
	}

	public boolean isUmlPrimitive() {
		return isUmlPrimitive;
	}

	public void setUmlPrimitive(boolean umlPrimitive) {
		isUmlPrimitive = umlPrimitive;
	}

	public List<UmlInterface> getRealizations() {
		return realizations;
	}

	public void setRealizations(List<UmlInterface> realizations) {
		this.realizations = realizations;
	}

	public void addRealization(UmlInterface umlInterface) {
		this.realizations.add(umlInterface);
	}

	public List<String> getRealizationIds() {
		return realizationIds;
	}

	public void setRealizationIds(List<String> realizationIds) {
		this.realizationIds = realizationIds;
	}

	public void addRealizationId(String realizationId) {
		this.realizationIds.add(realizationId);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UML Classname: ").append(getName());
		
		if(getProperties().size() > 0) {
			builder.append("\n").append("Properties:");
			for(UmlProperty property : getProperties()) {
				builder.append("\n\t").append(property);
			}
		}
		
		if(getTags().size() > 0) {
			builder.append("\n").append("Tags:");
			for(TaggedValue tag : getTags()) {
				builder.append("\n\t").append(tag);
			}
		}
		
		if(getGeneralizations().size() > 0) {
			builder.append("\n").append("Parents:");
			for(BaseClassifier parent : getGeneralizations()) {
				builder.append("\n\t").append(parent.getName());
			}
		}
		builder.append("\n");
		return builder.toString();
	}
	
	public void findClassForId(UmlModel model) {
		for(String id: getGeneralizationIds()) {
			if(id == null) {
				System.out.println("Class has null parent ID: " + getName());
				continue;
			}
			UmlClass umlClass = (UmlClass)model.getObjectById(id);
			if(umlClass != null) {
				getGeneralizations().add(umlClass);
			} else {
				throw new RuntimeException("Class not found for ID: " + id + " and class name " + getName());
			}
		}

		for(String id: getRealizationIds()) {
			if(id == null) {
				System.out.println("Class has null interface ID: " + getName());
				continue;
			}
			UmlInterface umlInterface = (UmlInterface)model.getObjectById(id);
			if(umlInterface != null) {
				getRealizations().add(umlInterface);
			} else {
				throw new RuntimeException("Interface not found for ID: " + id + " and class name " + getName());
			}
		}
		
		for(UmlProperty property : getProperties()) {
			property.findTypeClassForTypeId(model);
		}

		if(this.getTemplateSignature() != null) {
			this.getTemplateSignature().populateTemplateSignature(this, model);
		}

		if(this.getTemplateBinding() != null) {
			this.getTemplateBinding().populateTemplateBinding(model);
		}

	}
	
	public UmlClass clone() {
		UmlClass clone = (UmlClass)super.clone();
		cloneClassifier(clone);
		clone.setUmlPrimitive(this.isUmlPrimitive());
		return clone;
	}
}
