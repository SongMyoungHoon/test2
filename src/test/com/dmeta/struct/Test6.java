package test.com.dmeta.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Test6 {

    public static void main(String[] args) {
    	
        Runnable task = () -> {           
            System.out.println("작업 완료.");
        };

        task.run(); // 새 스레드로 실행
        
        
        
        Thread t = new Thread(() -> { 
        	int a = 0;
        	while(a<3) {
        		System.out.println("a"+a);
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		a++;
        		}
        	}
        );
        
        t.start();
        
        Consumer<String> con = name -> System.out.println("test"+name);
        
        con.accept("22");
        
        Supplier<String> hello =() -> "sssss";
        
        System.out.println(hello.get());
        
        Function<String,Integer> getlength = str -> str.length();
        
        System.out.println(getlength.apply("test"));
        
        Predicate<Integer> isEven = n -> n%2 == 0;
        System.out.println(isEven.test(4));
        
        List<String> names = new ArrayList<String>();
        
        names.add("s0");
        names.add("s1");
        names.add("s2");
        names.add("s3");
        names.add("s4");
        
        names.stream();
        
        List<String> sList = names.stream()
                .filter(name -> name.contains("1")||name.contains("2"))
                .collect(Collectors.toList());
        
        System.out.println(sList.toString());
        int a =0;
        
    }
    
   
}
