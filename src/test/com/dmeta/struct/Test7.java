package test.com.dmeta.struct;

public class Test7 {

	public static void main(String[] args) {
		int a = 5;
		int b = 3; 
		
		System.out.println(unsignedByte(a&b)); // and 연산 둘다 1인경우
		System.out.println(unsignedByte(a|b)); // 둘중 한개라도 1인경우
		System.out.println(unsignedByte(a^b)); // 다르면 1
		System.out.println(unsignedByte(~a)); //반전
		
		System.out.println(unsignedByte(a << 1));  //왼쪽으로 1비트 이동 (×2)
		System.out.println(unsignedByte(a << 2));  //왼쪽으로 2비트 이동 (×4)

		System.out.println(unsignedByte(a >> 1));  //오른쪽으로 1비트 이동 (/2)
		System.out.println(unsignedByte(a >> 2));  //오른쪽으로 2비트 이동 (/4)
		
		
	}
	
	public static int unsignedByte(int i) {
		return  i & 0xFF;
	}
}
