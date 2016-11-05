package guru.mwangaza.uml;

import guru.mwangaza.core.utils.CompositeIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void buildIndex() {
		for(Identifiable item : idToObjectMap.values()) {
			nameToObjectMap.put(item.getName(), item);
		}
	}
}
