package guru.mwangaza.uml;

public enum UmlPrimitiveEnum {

    STRING (new UmlClass("String", true)),
    INTEGER (new UmlClass("Integer", true)),
    FLOAT (new UmlClass("Float", true)),
    DOUBLE (new UmlClass("Double", true)),
    DATE (new UmlClass("Date",true));

    private UmlClass primitive;

    UmlPrimitiveEnum(UmlClass primitive) {
        this.primitive = primitive;
    }

    public UmlClass getPrimitive() {
        return primitive;
    }
}
