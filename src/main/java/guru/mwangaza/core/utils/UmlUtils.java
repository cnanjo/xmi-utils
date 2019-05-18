package guru.mwangaza.core.utils;

import guru.mwangaza.uml.BaseClassifier;
import guru.mwangaza.uml.UmlPackage;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class UmlUtils {

    public static final Character DEFAULT_PACKAGE_DELIMITER = '.';

    /**
     * Converts path into a package tree
     *
     * @param path
     * @return
     */
    public static UmlPackage buildPackageTreeFromPath(String path) {
        UmlPackage root = null;
        UmlPackage currentPackage = null;
        if(StringUtils.isNotBlank(path)) {
            String[] components = path.split("\\" + DEFAULT_PACKAGE_DELIMITER);
            for(int index = 0; index < components.length; index++) {
                if(index == 0) {
                    root = new UmlPackage(components[index]);
                    currentPackage = root;
                } else {
                    UmlPackage temp = new UmlPackage(components[index]);
                    currentPackage.addPackage(temp);
                    currentPackage = temp;
                }
            }
        }
        return root;
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
        StringBuilder path = new StringBuilder();
        Stack stack = new Stack();
        UmlPackage currentPackage = leafPackage;
        stack.push(currentPackage.getName());
        while(currentPackage.getParentPackage() != null) {
            currentPackage = currentPackage.getParentPackage();
            stack.push(currentPackage.getName());
        }
        while(!stack.empty()) {
            path.append(stack.pop()).append(delimiter);
        }
        return path.toString();
    }

}
