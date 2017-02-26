package guru.mwangaza.uml;

import guru.mwangaza.core.utils.CompositeIdentifier;

import java.util.*;

/**
 * Represents a UML class model.
 * 
 * @author cnanjo
 *
 */
public class UmlModel extends UmlComponent implements Identifiable {
	
	private List<UmlPackage> packages;
	private Map<String, Identifiable> idToObjectMap;
	private Map<String, Identifiable> nameToObjectMap;
	private Map<String, UmlModel> dependencies;

	public UmlModel() {
		packages = new ArrayList<UmlPackage>();
		idToObjectMap = new HashMap<String, Identifiable>();
		nameToObjectMap = new HashMap<String, Identifiable>();
		dependencies = new HashMap<String, UmlModel>();
	}
	
	public UmlModel(String name) {
		this();
		setName(name);
	}

	public void addDependency(String modelName, UmlModel model) {
		dependencies.put(modelName, model);
	}

	public UmlModel getDependency(String modelName) {
		return dependencies.get(modelName);
	}

	public void setDependencies(Map<String, UmlModel> dependencies) {
		this.dependencies = dependencies;
	}

	public Map<String, UmlModel> getDependencies() {
		return dependencies;
	}
	
	public void addPackage(UmlPackage modelPackage) {
		packages.add(modelPackage);
	}
	
	public List<UmlPackage> getPackages() {
		return packages;
	}
	
	public String toString() {
		return "Model: " + packages;
	}
	
	public Object getObjectById(String id) {
		Object returnValue;
		CompositeIdentifier compositeIdentifier = CompositeIdentifier.getCompositeIdentifier(id);
		if(compositeIdentifier.isComposite()) {
			UmlModel dependency = getDependency(compositeIdentifier.getModelName());
			if(dependency == null) {
				throw new RuntimeException("Invalid dependency " + compositeIdentifier.getModelName());
			}
			returnValue = dependency.getIdMap().get(compositeIdentifier.getIdentifier());
		} else {
			returnValue = getIdMap().get(compositeIdentifier.getIdentifier());
		}
		return returnValue;
	}
	
	public Object getObjectByName(String name) {
		return nameToObjectMap.get(name);
	}
	
	public void putObject(String id, Identifiable item) {
		idToObjectMap.put(id, item);
	}

	public Map<String,Identifiable> getIdMap() {
		return idToObjectMap;
	}
	
	public void populateTypes() {
		for(UmlPackage umlPackage: packages) {
			umlPackage.initializePackage(this);
		}
	}

	/**
	 * Two kinds of generic template signature exist: Ones that define inline their parameters and ones that reference
	 * the parameters defined in other template signature definitions. For the later, if they are processed before signature
	 * that defines their parameters, the parameter remains a reference without a name and possibly a type. Hence, once the
	 * components of a model are loaded, it is necessary to do a pass-through to identify all parameter references and flesh
	 * them out. Doing so requires resolving the parameter reference against its actual definition and setting the state of
	 * the reference to match the definition.
	 */
	public void handleParameterReferences() {
		Collection<Identifiable> identifiables = idToObjectMap.values();
		for(Identifiable identifiable : identifiables) {
			if(identifiable instanceof UmlTemplateSignature) {
				UmlTemplateSignature signature = (UmlTemplateSignature)identifiable;
				signature.getParameters().forEach(parameter -> {
					if(parameter.isParameterReference()) {
						UmlTemplateParameter definedParameter = (UmlTemplateParameter) idToObjectMap.get(parameter.getId());
						parameter.setName(definedParameter.getName());
						parameter.setType(definedParameter.getType());
						parameter.setTypeId(definedParameter.getTypeId());
					}
				});
			}
		}
	}
	
	public void buildIndex() {
		for(Identifiable item : idToObjectMap.values()) {
			nameToObjectMap.put(item.getName(), item);
		}
	}
}
