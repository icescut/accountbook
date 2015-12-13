package per.icescut.entry;

public enum AType {
    Pay("支出",0),Income("收入",1),Transfer("转帐",2);
    
    public static AType getTypeByIndex(int index) {
	for(AType t : AType.values()) {
	    if(t.index == index) {
		return t;
	    }
	}
	return null;
    }
    
    public static int getIndexByName(String name) {
	for(AType t : AType.values()) {
	    if(t.name.equals(name)) {
		return t.index;
	    }
	}
	return -1;
    }
    
    public static String[] names() {
	AType[] values = AType.values();
	String[] names = new String[values.length];
	int i = 0;
	for(AType t : values) {
	    names[i++] = t.getName();
	}
	return names;
    }
    
    public static AType getTypeByName(String type) {
	if(type.equals(AType.Pay.getName())) {
	    return AType.Pay;
	} else if(type.equals(AType.Income.getName())) {
	    return AType.Income;
	} else {
	    return AType.Transfer;
	}
    }
    
    public String getName() {
	return name;
    }
    
    public int getIndex() {
	return index;
    }
    
    private AType(String name, int index) {
	this.name = name;
	this.index = index;
    }
    
    private String name;
    private int index;
    
}
