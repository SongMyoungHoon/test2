package test.com.dmeta.struct;

import java.nio.ByteBuffer;

public class Test5 {

	public static void main(String[] args) {
		
		byte b =(byte) 0xff;
		
		byte[] bb = new byte[2];
		
		bb[0] = (byte) 0xff;
		bb[1] = (byte) 0xff;
		
		int combined = ((Byte.toUnsignedInt(bb[0]) << 8) | Byte.toUnsignedInt(bb[1]));
		
		System.out.println("Combined unsigned int = " + combined);
		
		System.out.println(0xffff==combined);
		
		int i = Integer.parseUnsignedInt("4294967295"); 
		
		System.out.println( Integer.toUnsignedString(i));
		
		
		System.out.println(bb);
		
		byte t = 0x0f;
		
		byte t2 = 0x10;
		System.out.println(t ==15);
		System.out.println(t2 ==16);
		
		
		long unsignedVal = 4294967295L;

        byte[] bytes = UnsignedUtil.unsignedIntToBytes(unsignedVal);
        System.out.println("HEX: " + UnsignedUtil.toHexString(bytes)); // FF FF FF FF

        long recovered = UnsignedUtil.bytesToUnsignedInt(bytes);
        System.out.println("Recovered: " + recovered); // 4294967295
		
	}
	


	public class UnsignedUtil {

	    // 🔹 int 값을 4바이트 배열로 변환 (Big-endian)
	    public static byte[] intToBytes(int value) {
	        return ByteBuffer.allocate(4).putInt(value).array();
	    }

	    // 🔹 4바이트 배열을 unsigned int로 변환 → long 으로 받음
	    public static long bytesToUnsignedInt(byte[] bytes) {
	        if (bytes.length != 4) throw new IllegalArgumentException("4바이트 배열만 허용됩니다.");
	        return ByteBuffer.wrap(bytes).getInt() & 0xFFFFFFFFL;
	    }

	    // 🔹 long 값을 4바이트 unsigned int 바이트 배열로 (하위 32비트만)
	    public static byte[] unsignedIntToBytes(long value) {
	        if (value < 0 || value > 0xFFFFFFFFL)
	            throw new IllegalArgumentException("0 ~ 4294967295 범위만 허용됩니다.");
	        return ByteBuffer.allocate(4).putInt((int) value).array();
	    }

	    // 🔹 바이트 배열 → 헥사 문자열
	    public static String toHexString(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (byte b : bytes) {
	            sb.append(String.format("%02X ", b));
	        }
	        return sb.toString().trim();
	    }

	    // 🔹 헥사 문자열 → 바이트 배열 (예: "FF 01 00 A4")
	    public static byte[] fromHexString(String hex) {
	        String[] tokens = hex.trim().split("\\s+");
	        byte[] bytes = new byte[tokens.length];
	        for (int i = 0; i < tokens.length; i++) {
	            bytes[i] = (byte) Integer.parseInt(tokens[i], 16);
	        }
	        return bytes;
	    }
	}

}

