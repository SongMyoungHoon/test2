package test.com.dmeta.struct;

public class Test3 {

	 public static void main(String[] args) {
	        int a = 0xAA;  // 16진수 0xAA = 10101010 (2진수)
	        int b = 0xCC;  // 16진수 0xCC = 11001100 (2진수)

	        int result = a ^ b;  // XOR 연산

	        System.out.println("A (0xAA): " + Integer.toBinaryString(a));
	        System.out.println("B (0xCC): " + Integer.toBinaryString(b));
	        System.out.println("A ⊕ B : " + Integer.toBinaryString(result));
	        
	        System.out.printf("결과 (16진수): 0x%X\n", result);
	    }
}
