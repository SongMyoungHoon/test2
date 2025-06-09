package test.com.dmeta;

import test.com.dmeta.struct.TeleService;
import test.com.dmeta.util.DmetaUtil;

public class Test22 {

	
    public static void main(String[] args) {
        // 테스트용 샘플 데이터 (바이트 배열 5개)
        byte[] b = {'a', 'b', 8, 9};

        // byteArrayToBitCharArray
        byte[] bitCharArray = byteArrayToBitCharArray(b);
        	System.out.println("bitCharArray: "+ new String(bitCharArray));
        
        
        // bitCharArrayToByteArray
        byte[] byteArray = bitCharArrayToByteArray(bitCharArray);
        for(byte a : byteArray) {
        	//System.out.println("byteArray: "+ (0xff&a));
        }
        
        try {
        	String className = "struct.com.dmeta.ai.struct.tr.T3";
            
            Class<?> clazz = Class.forName(className); // 클래스 로드
            
    		TeleService task = (TeleService) clazz.newInstance();
            
    		//task.parseMsg(bitCharArray);
    		
    		System.out.println(new String( bitCharArrayToByteArray(DmetaUtil.objectToByteArray(task.getBodyElementData("ID")))));
    		
            //task.printMsg();
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        
    }
    
    
    /**
     * byte[] → '0' / '1' 문자 배열 (비트 문자열 표현)
     * 예: 25 → 00011001 → ['0','0','0','1','1','0','0','1']
     */
    public static byte[] byteArrayToBitCharArray(byte[] byteArray) {
        byte[] bitCharArray = new byte[byteArray.length * 8];

        for (int i = 0; i < byteArray.length; i++) {
            for (int j = 0; j < 8; j++) {
                int bitIndex = i * 8 + j;
                boolean isSet = (byteArray[i] & (1 << (7 - j))) != 0;
                bitCharArray[bitIndex] = (byte) (isSet ? '1' : '0');
            }
        }

        return bitCharArray;
    }
    
    /**
     * '0' / '1'로 구성된 byte 배열 → 원래의 byte[]로 복원
     */
    public static byte[] bitCharArrayToByteArray(byte[] bitCharArray) {
        int len = (bitCharArray.length + 7) / 8;
        byte[] byteArray = new byte[len];

        for (int i = 0; i < bitCharArray.length; i++) {
            if (bitCharArray[i] == '1') {
                byteArray[i / 8] |= (1 << (7 - (i % 8)));
            }
        }

        return byteArray;
    }
}
