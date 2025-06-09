package test.com.dmeta;

public class BitToByte {

	 public static String toBitString(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (byte b : bytes) {
	            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
	        }
	        return sb.toString();
	    }

	    public static void main(String[] args) {
	        byte[] input = new byte[] { (byte) 0x01, (byte) 0xf2 };
	        String bitString = toBitString(input);
	        System.out.println("비트 스트링: " + bitString);
	        
	        byte[] tmp1 = bitString.getBytes();
	        
	        System.out.println(tmp1.length);
	        System.out.println(new String(tmp1));
	    }
}
