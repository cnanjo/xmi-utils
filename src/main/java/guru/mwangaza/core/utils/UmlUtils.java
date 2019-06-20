package guru.mwangaza.core.utils;

import guru.mwangaza.uml.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class UmlUtils {

    public static final Character DEFAULT_PACKAGE_DELIMITER = '.';

    /**
     * Converts path into a package containment hierarchy and returns the root of the path.
     *
     * @param path
     * @return
     */
    public static UmlPackage buildPackageTreeFromPath(String path) {
        return buildPackageTreeFromPath(path, false);
    }

    /**
     * Converts path into a package containment hierarchy.
     *
     * @param path The package input path
     * @param returnLeaf If true return the leaf package in the path (package with no further children packages).
     *                   Else, return the root of the package (package with no parent package).
     * @return Either the leaf package or the root package of the containment hierarchy.
     */
    public static UmlPackage buildPackageTreeFromPath(String path, boolean returnLeaf) {
        UmlPackage root = null;
        UmlPackage currentLeafPackage = null;
        if(StringUtils.isNotBlank(path)) {
            String[] components = path.split("\\" + DEFAULT_PACKAGE_DELIMITER);
            for(int index = 0; index < components.length; index++) {
                if(index == 0) {
                    root = new UmlPackage(components[index]);
                    currentLeafPackage = root;
                } else {
                    UmlPackage temp = new UmlPackage(components[index]);
                    currentLeafPackage.addPackage(temp);
                    currentLeafPackage = temp;
                }
            }
        }
        if(returnLeaf) {
            return currentLeafPackage;
        } else {
            return root;
        }
    }

    /**
     * Returns the root package for the package tree that contains this class at the leaf-level.
     *
     * @param classifier
     * @return
     */
    public static UmlPackage getRootPackage(BaseClassifier classifier) {

        UmlPackage parent = classifier.getParentPackage();;

        while(parent != null && parent.getParentPackage() != null) {
            parent = parent.getParentPackage();
        }

        return parent;

    }

    public static Map<String, List<UmlPackage>> indexPackagesByName(UmlPackage root) {
        Map<String,List<UmlPackage>> index = new HashMap<>();
        indexPackagesByName(root, index);
        return index;
    }

    public static void indexPackagesByName(UmlPackage packageToIndex, Map<String, List<UmlPackage>> index) {
        if(packageToIndex != null) {
            String key = packageToIndex.getName();
            if(!index.containsKey(key)) {
                List<UmlPackage> packages = new ArrayList<>();
                index.put(key, packages);
            }
            index.get(key).add(packageToIndex);

            //Handle children
            if(packageToIndex.getPackages() != null) {
                packageToIndex.getPackages().forEach(umlPackage -> {
                    indexPackagesByName(umlPackage, index);
                });
            }
        }
    }

    public static String getPackageHierarchyAsRelativeFilePath(UmlPackage leafPackage, String delimiter) {
        List<String> pathComponentList = new ArrayList<>();
        Stack<String> stack = new Stack();
        UmlPackage currentPackage = leafPackage;
        stack.push(currentPackage.getName());
        while(currentPackage.getParentPackage() != null) {
            currentPackage = currentPackage.getParentPackage();
            stack.push(currentPackage.getName());
        }
        while(!stack.isEmpty()) {
            pathComponentList.add(stack.pop());
        }
        return String.join(delimiter, pathComponentList);
    }

    public static BaseClassifier buildClassifierFromCanonicalClassName(String canonicalClassName, boolean asInterface) {
        if(canonicalClassName.indexOf('.') < 0) {
            if(asInterface) {
                return new UmlInterface(canonicalClassName);
            } else {
                return new UmlClass(canonicalClassName);
            }
        } else {
            String packagePath = canonicalClassName.substring(0, canonicalClassName.lastIndexOf('.'));
            String className = canonicalClassName.substring(canonicalClassName.lastIndexOf('.') + 1);
            UmlPackage root = buildPackageTreeFromPath(packagePath);
            UmlPackage leaf = root;
            while(leaf.hasChildrenPackages()) {
                leaf = leaf.getPackages().get(0);//Single branch so okay
            }

            BaseClassifier baseClassifier = null;
            if(asInterface) {
                baseClassifier = new UmlInterface(canonicalClassName);
                leaf.addInterface((UmlInterface)baseClassifier);
            } else {
                baseClassifier = new UmlClass(canonicalClassName);
                leaf.addClass((UmlClass)baseClassifier);
            }
            return baseClassifier;
        }
    }

    public static boolean hasAncestors(BaseClassifier classifier, String ancestorName) {
        if(classifier.getName().equalsIgnoreCase(ancestorName)) {
            return true;
        } else {
            return hasAncestors(classifier.getGeneralizations(), ancestorName);
        }
    }

    protected static boolean hasAncestors(List<BaseClassifier> parents, String parentName) {
        boolean found = false;
        for(BaseClassifier parent : parents) {
            if(parent.getName().equalsIgnoreCase(parentName)) {
                found = true;
            } else {
                if(parent.getGeneralizations() != null) {
                    found = hasAncestors(parent.getGeneralizations(), parentName);
                }
            }
        }
        return found;
    }

    public static UmlInterface cloneAndFlattenInterface(UmlInterface source) {
        UmlInterface target = source.clone();

        if (source.hasGeneralizations()) {
            List<UmlProperty> properties = new ArrayList<>();
            collectAttributes(properties, source);
            target.setProperties(properties);
        }

        return target;
    }

    public static void collectAttributes(List<UmlProperty> properties, UmlInterface source) {
        if (source.hasGeneralizations()) {
            source.getGeneralizations().forEach(parent -> {
                collectAttributes(properties, (UmlInterface) parent);
            });
        }
        properties.addAll(source.getProperties());
    }

}
