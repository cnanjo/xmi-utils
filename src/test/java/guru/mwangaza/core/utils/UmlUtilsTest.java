package guru.mwangaza.core.utils;

import guru.mwangaza.uml.UmlClass;
import guru.mwangaza.uml.UmlPackage;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UmlUtilsTest {

    @Test
    public void buildPackageTreeFromPath() {
        UmlPackage root = UmlUtils.buildPackageTreeFromPath("parent.child.grandchild");
        assertEquals("parent", root.getName());
        assertEquals("child", root.getPackages().get(0).getName());
        assertEquals("grandchild", root.getPackages().get(0).getPackages().get(0).getName());
    }

    @Test
    public void getRootPackage() {
        UmlPackage root = UmlUtils.buildPackageTreeFromPath("parent.child");
        UmlClass testClass = new UmlClass("TestClass");
        UmlPackage grandChild = new UmlPackage("greatgrandchild");
        UmlPackage greatGrandChild = new UmlPackage("greatgrandchild");
        grandChild.addPackage(greatGrandChild);
        testClass.setParentPackage(greatGrandChild);
        Map<String, List<UmlPackage>> index = UmlUtils.indexPackagesByName(root);
        index.get("child").get(0).addPackage(grandChild);
        UmlPackage otherRoot = UmlUtils.getRootPackage(testClass);
        assert(root == otherRoot);

    }

}