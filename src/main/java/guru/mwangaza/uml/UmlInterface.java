package guru.mwangaza.uml;

import java.util.ArrayList;

public class UmlInterface extends BaseClassifier {

    public UmlInterface() {
        super();
    }

    public UmlInterface(String name) {
        this();
        setName(name);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UML Interface Name: ").append(getName());

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

    public void findInterfaceForId(UmlModel model) {
        for(String id: getGeneralizationIds()) {
            if(id == null) {
                System.out.println("Interface has null parent ID: " + getName());
                continue;
            }
            UmlInterface umlInterface = (UmlInterface)model.getObjectById(id);
            if(umlInterface != null) {
                getGeneralizations().add(umlInterface);
            } else {
                throw new RuntimeException("Interface not found for ID: " + id + " and interface name " + getName());
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

    public UmlInterface clone() {
        UmlInterface clone = (UmlInterface)super.clone();
        clone.setDescription(getDescription());
        clone.setGeneralizations(new ArrayList<BaseClassifier>());
        clone.getGeneralizations().addAll(getGeneralizations());
        clone.setGeneralizationIds(new ArrayList<String>());
        clone.getGeneralizationIds().addAll(this.getGeneralizationIds());
        clone.setProperties(new ArrayList<UmlProperty>());
        clone.getProperties().addAll(this.getProperties());
        return clone;
    }
}
