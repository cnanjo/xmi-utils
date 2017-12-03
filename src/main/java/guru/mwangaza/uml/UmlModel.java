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
	private Map<String, List<UmlProfileDefinition>> profileDefinitionMap;

	public UmlModel() {
		packages = new ArrayList<UmlPackage>();
		idToObjectMap = new HashMap<String, Identifiable>();
		nameToObjectMap = new HashMap<String, Identifiable>();
		dependencies = new HashMap<String, UmlModel>();
		profileDefinitionMap = new HashMap<>();
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

	public Map<String, List<UmlProfileDefinition>> getProfileDefinitionMap() {
		return profileDefinitionMap;
	}

	public void setProfileDefinitionMap(Map<String, List<UmlProfileDefinition>> profileDefinitionMap) {
		this.profileDefinitionMap = profileDefinitionMap;
	}

	public void addProfile(UmlProfileDefinition profileDefinition) {
		List<UmlProfileDefinition> profiles = this.profileDefinitionMap.get(profileDefinition.getName());
		if(profiles == null) {
			profiles = new ArrayList<UmlProfileDefinition>();
			this.profileDefinitionMap.put(profileDefinition.getName(), profiles);
		}
		profiles.add(profileDefinition);
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
