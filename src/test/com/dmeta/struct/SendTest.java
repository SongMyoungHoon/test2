package test.com.dmeta.struct;

import test.com.dmeta.util.DmetaUtil;

public class SendTest {

	public static void main(String[] args) {
		
		//T1 t1 = new T1();
		
		String t = "abbccc";
		String date =  "20250321100000";
		byte[] b = new byte[29];
		
		int off = 0;
		
		b[0] = (byte) 0xFE;
		b[1] = (byte) 0xFF;
		b[2] = (byte) 0xFF;
		
		off = 3;
		
		System.arraycopy(date.getBytes(), 0 , b, off, date.getBytes().length);
		
		off += date.getBytes().length;
		
		b[off] = (byte) 0xFF;
		off +=1;
		b[off] = (byte) 0xFF;
		off +=1;
		b[off] = (byte) 0xFF;
		off +=1;
		b[off] = (byte) 0xFF;
		off +=1;
		b[off] = (byte) 0x00;
		off +=1;
		b[off] = (byte) 0x1D;
		off +=1;
		System.arraycopy(t.getBytes(), 0 , b, off, t.getBytes().length);
		
		byte[] bb = new byte[7];
		
		bb[0] = (byte) 0xFE;
		System.arraycopy(t.getBytes(), 0 , bb, 1, t.getBytes().length);
		try {
			
			String className = "test.com.dmeta.struct.tele.T1";
			
			Class<?> clazz = Class.forName(className); // 클래스 로드
	        
	        TeleService task = (TeleService) clazz.newInstance();
	       
	        //task.setMsg(b);
	        
	        //task.printMsg();
	        
	        task.execute();
	        
	        int a =  DmetaUtil.objectToInt( task.getHeaderElementData("TYPE"));
	        
	        System.out.println("a"+a);
	        
	        System.out.println(a==0xfE);
	        
	        System.out.println((a+1)==(0xfE+1));
	        
	        
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
