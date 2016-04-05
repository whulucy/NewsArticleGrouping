package P2;


public class Debug {
	public static final boolean isActivated = true;
    public static void print(String s){
    	if(isActivated) {
    		System.out.println(s);
    	}
    }
    public static void print(Object o){
    	if(isActivated) {
    		System.out.println(o.toString());
    	}
    }
}
