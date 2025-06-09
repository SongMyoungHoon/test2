package test.com.dmeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TTest {

	public static void main(String[] args) {
		Map<String, List<String>> map = new HashMap<>();
		Map<String, String> map3 = new HashMap<>();
		map.put("colors", new ArrayList<>(List.of("red", "blue")));
		map3.put("Test","test");
		

		List<String> colorList = map.get("colors");
		String s = map3.get("Test");
		s = "ss";
		colorList.add("green");

		//System.out.println(map.get("colors"));
		//System.out.println("테스트");
		int a =1;
		a = 1-6;
		System.out.println(a);
	}
}

