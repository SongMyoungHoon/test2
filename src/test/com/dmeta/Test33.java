package test.com.dmeta;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Test33 {

	static List list  = new ArrayList();
	
	public static void main(String[] args) {
		
		list.add("STRING");
		
		String a =String.valueOf( list.get(0));
		
		System.out.println(list.toString());
	}
}
