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
import java.util.List;

/**
 * Class represents a simple UML package. UML packages
 * can be used to group related classes.
 * 
 * @author cnanjo
 *
 */
public class UmlPackage extends UmlComponent implements Cloneable {
	
	private List<UmlPackage> packages = new ArrayList<UmlPackage>();
	private List<UmlClass> classes = new ArrayList<UmlClass>();
	private List<UmlInterface> interfaces = new ArrayList<UmlInterface>();
	private UmlPackage parentPackage;

	public UmlPackage(String name) {
		setName(name);
	}
	
	public void addPackage(UmlPackage umlPackage) {
		if(!packages.contains(umlPackage)) {
			packages.add(umlPackage);
			umlPackage.setParentPackage(this);
		}
	}
	
	public List<UmlPackage> getPackages() {
		return packages;
	}

	public UmlPackage getPackageWithName(String name) {
		UmlPackage found = null;
		for(int index = 0; index < packages.size(); index++) {
			if(packages.get(index).getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
				found = packages.get(index);
				break;
			}
		}
		return found;
	}

	public void setPackages(List<UmlPackage> packages) {
		this.packages = packages;
	}

	public void setClasses(List<UmlClass> classes) {
		this.classes = classes;
	}

	public void setInterfaces(List<UmlInterface> interfaces) {
		this.interfaces = interfaces;
	}

	public boolean hasChildrenPackages() {
		return packages != null && packages.size() > 0;
	}
	
	public void addClass(UmlClass umlClass) {
		classes.add(umlClass);
		umlClass.setParentPackage(this);
	}

	public void addInterface(UmlInterface umlInterface) {
		interfaces.add(umlInterface);
	}
	
	public List<UmlClass> getClasses() {
		return classes;
	}

	public List<UmlInterface> getInterfaces() {
		return interfaces;
	}

	public UmlPackage getParentPackage() {
		return parentPackage;
	}

	public void setParentPackage(UmlPackage parentPackage) {
		this.parentPackage = parentPackage;
	}

	public void initializePackage(UmlModel model) {
		for(UmlClass umlClass : getClasses()) {
			umlClass.findClassForId(model);
		}

		for(UmlInterface umlInterface : getInterfaces()) {
			umlInterface.findInterfaceForId(model);
		}

		for(UmlPackage umlPackage : packages) {
			umlPackage.initializePackage(model);
		}
	}

	public UmlPackage clone() {
		UmlPackage clone = (UmlPackage)super.clone();
		clone.setParentPackage(this.getParentPackage());
		clone.setPackages(this.getPackages());
		clone.setInterfaces(this.getInterfaces());
		clone.setClasses(this.getClasses());
		return clone;
	}

	public String toString() {
		return "Package(" + getName() + ") " + packages + "," + classes;
	}
}
