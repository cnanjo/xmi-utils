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
public class UmlClass extends UmlComponent implements Identifiable, Cloneable {

	private String description;
	private List<UmlClass> generalizations = new ArrayList<UmlClass>();
	private List<String> generalizationIds = new ArrayList<String>();
	private List<UmlProperty> properties = new ArrayList<UmlProperty>();
	private UmlTemplateSignature templateSignature;
	private UmlTemplateBinding templateBinding;
	private UmlModel model;
	private boolean isPrimitive = false;
	private boolean isAbstract = false;
	private boolean isBinding = false;
	
	public UmlClass() {}
	
	public UmlClass(String name) {
		this();
		setName(name);
	}

	public List<UmlProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<UmlProperty> attributes) {
		this.properties = attributes;
	}

	public void addProperty(UmlProperty attribute) {
		properties.add(attribute);
	}
	
	public List<UmlClass> getGeneralizations() {
		return generalizations;
	}

	public void setGeneralizations(List<UmlClass> generalizations) {
		this.generalizations = generalizations;
	}

	public void addGeneralization(UmlClass generalization) {
		generalizations.add(generalization);
	}
	
	public List<String> getGeneralizationIds() {
		return generalizationIds;
	}

	public void setGeneralizationIds(List<String> generalizationIds) {
		this.generalizationIds = generalizationIds;
	}

	public void addGeneralizationId(String generalizationId) {
		generalizationIds.add(generalizationId);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public UmlModel getModel() {
		return model;
	}

	public void setModel(UmlModel model) {
		this.model = model;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public boolean isGenericType() {
		return templateSignature != null;
	}

	public UmlTemplateSignature getTemplateSignature() {
		return templateSignature;
	}

	public void setTemplateSignature(UmlTemplateSignature templateSignature) {
		this.templateSignature = templateSignature;
	}

	public UmlTemplateBinding getTemplateBinding() {
		return templateBinding;
	}

	public void setTemplateBinding(UmlTemplateBinding templateBinding) {
		this.templateBinding = templateBinding;
	}

	public boolean isBinding() {
		return isBinding;
	}

	public void setBinding(boolean binding) {
		isBinding = binding;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UML Classname: ").append(getName());
		
		if(properties.size() > 0) {
			builder.append("\n").append("Properties:");
			for(UmlProperty property : properties) {
				builder.append("\n\t").append(property);
			}
		}
		
		if(getTags().size() > 0) {
			builder.append("\n").append("Tags:");
			for(TaggedValue tag : getTags()) {
				builder.append("\n\t").append(tag);
			}
		}
		
		if(generalizations.size() > 0) {
			builder.append("\n").append("Parents:");
			for(UmlClass parent : generalizations) {
				builder.append("\n\t").append(parent.getName());
			}
		}
		builder.append("\n");
		return builder.toString();
	}
	
	public void findClassForId(UmlModel model) {
		for(String id: generalizationIds) {
			if(id == null) {
				System.out.println("Class has null parent ID: " + getName());
				continue;
			}
			UmlClass umlClass = (UmlClass)model.getObjectById(id);
			if(umlClass != null) {
				generalizations.add(umlClass);
			} else {
				throw new RuntimeException("Class not found for ID: " + id + " and class name " + getName());
			}
		}
		
		for(UmlProperty property : properties) {
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
		clone.setDescription(this.description);
		clone.setGeneralizations(new ArrayList<UmlClass>());
		clone.getGeneralizations().addAll(this.generalizations);
		clone.setGeneralizationIds(new ArrayList<String>());
		clone.getGeneralizationIds().addAll(this.getGeneralizationIds());
		clone.setProperties(new ArrayList<UmlProperty>());
		clone.getProperties().addAll(this.getProperties());
		return clone;
	}
}
