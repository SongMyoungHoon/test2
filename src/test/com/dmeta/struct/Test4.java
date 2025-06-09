package test.com.dmeta.struct;

import java.util.Arrays;

public class Test4 {
	public static void main(String[] args) {
		byte b = (byte) 0xFE;
		
		byte b2 = (byte) 0x01;
		
		System.out.println(b);
		
		byte[] bb = {b,b2};
		
		byte[] cc = new byte[10];
		
		String s = "abcd1234";
		
		System.arraycopy(bb, 0, cc, 0, bb.length);
		System.arraycopy(s.getBytes(), 0, cc, 2, s.getBytes().length);
		
		System.out.println(Arrays.toString(bb));
		
		System.out.println("cc:"+new String(cc));
		
		byte[] d = new byte[2];
		
		System.arraycopy(cc , 0, d, 0, d.length);
		
		System.out.println("d"+Arrays.toString(d));
		
		byte[] e = new byte[8];
		
		System.arraycopy(cc , 2, e , 0, e.length);
		
		System.out.println("e"+new String(e));
		
		byte f = d[0];
		
		if(Byte.toUnsignedInt(f)  ==0xFE)
			System.out.println("OXFE++");
		else
			System.out.println("-2-2");
		
		
		System.out.println(Byte.toUnsignedInt(f));
		
		int t = 0xFFFF;
		
		System.out.println(t);
	}
}
