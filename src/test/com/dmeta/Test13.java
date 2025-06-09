package test.com.dmeta;

public class Test13 {

	    public static void main(String[] args) {
	        new Child();
	        System.out.println(Parent.total);
	    }
	}
	 
	 
	class Parent {
	    static int total = 0;
	    int v = 1;
	 
	    public Parent() {
	    	System.out.println("#########");
	        total += (++v);
	        System.out.println(total);
	        show();    
	    }
	 
	    public void show() {
	        total += total;
	       System.out.println("!!!!!!!!"+ total);
	    }
	}
	 
	 
	class Child extends Parent {
	    int v = 10;
	 
	    public Child() {
	        v += 2;
	        total += v++;
	        System.out.println("total" + total);
	        show();
	    }
	 
	    @Override
	    public void show() {
	        total += total * 2; 
	        System.out.println("@@@@@@@"+ total);
	    }
	
	 
}
	
	
	
	
