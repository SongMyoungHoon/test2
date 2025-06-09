package test.com.dmeta;

public class Test4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String s = "송송송";
		
		String s = "abc";
		
		byte[] b = new byte[3];
		
		System.arraycopy(s.getBytes(), 0, b, 0, s.getBytes().length);
		
		System.out.println("b"+ new String(b));
		
	}
}
