package test.com.dmeta.struct;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import test.com.dmeta.struct.constant.Constant;
import test.com.dmeta.struct.tele.AckMsg;
import test.com.dmeta.struct.tele.SendHeader;
import test.com.dmeta.util.DmetaUtil;

public abstract class TeleService {

	protected SendHeader sndHeader;
	protected AckMsg ackMsg;

	private Map<String, TeleElement> mBodyMsg = new LinkedHashMap<String, TeleElement>();
	private Map<String, TeleElement> mHeaderMsg = new LinkedHashMap<String, TeleElement>();
	
	private List<LinkedHashMap<String, TeleElement>> lLoopBody = new ArrayList<LinkedHashMap<String, TeleElement>>(); 
	private String currentLoopKey = null;
	
	public TeleService() {

	}
	
	protected abstract void startSvrMain();
	
	protected abstract void getSendMsg();

	public void addHeaderMsg(TeleElement tMsg) {
		mHeaderMsg.put(tMsg.getKey(), tMsg);
	}
	
	protected void applySendHeader() { 
		if (sndHeader != null) { 
			for (Map.Entry<String,TeleElement> entry : sndHeader.getHeaderMap().entrySet()) {
				mHeaderMsg.put(entry.getKey(), entry.getValue()); 
			} 
		} 
	}

	public void addBodyMsg(TeleElement element) {
		mBodyMsg.put(element.getKey(), element);
		
		if(element.getType().equals("R"))
			currentLoopKey = element.getKey();
	}
	
	public void addsubBodyMsg(TeleElement element) {
		LinkedHashMap<String, TeleElement> map = new LinkedHashMap<String, TeleElement>();
		
	}
	
	
	public void initSendHeader() {
	    this.sndHeader = new SendHeader();
	    applySendHeader();
	}


	public Map<String, TeleElement> getHeaderMap() {
		return mHeaderMsg;
	}

	public void printMsg() {

		try {
			
			byte[] msg = new byte[getToTalLen()];
			
			
			
			for (String key : mHeaderMsg.keySet()) {

				if (mHeaderMsg.get(key).strMsgType.equals("B")) 
					System.out.println("[ "+key +"]["+ DmetaUtil.byteArrayToInt( mHeaderMsg.get(key).data) +"]");
				else
					System.out.println("[" + key + "]:[" + new String(mHeaderMsg.get(key).data) + "]");

			}

			for (String key : mBodyMsg.keySet()) {

				if (mBodyMsg.get(key).strMsgType.equals("B")) {
					for (int i = 0; i < mBodyMsg.get(key).nLen; i++) {
						System.out.println(key + Byte.toUnsignedInt(mBodyMsg.get(key).data[i]));
					}
				} else
					System.out.println("[" + key + "]:[" + new String(mBodyMsg.get(key).data) + "]");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public void setDataHeader(byte[] bmsg) {
		setHeaderElementData("TYPE"      , Constant.DATA_SEND );
		setHeaderElementData("SEQ"       , 6000 );
		setHeaderElementData("DATE"      , "20250324151300".getBytes());
		setHeaderElementData("TOTAL_LEN" , getToTalLen()  );
		
	}
	
	public byte[] getBodyData() {
		
		byte[] rByte =  new byte[getBodyLen()];
		
		int nOffSet = 0;
		
		for (String key : mBodyMsg.keySet()) {

			if (mBodyMsg.get(key).strMsgType.equals("B")) {
				for (int i = 0; i < mBodyMsg.get(key).nLen; i++) {
					rByte[nOffSet] = mBodyMsg.get(key).data[0];
					nOffSet ++;
				}
			} else {
				System.arraycopy(mBodyMsg.get(key).data , 0 , rByte, nOffSet, mBodyMsg.get(key).nLen);
				nOffSet += mBodyMsg.get(key).nLen;
			}
		}
		
		return rByte;
	}
	

	public void setMsg(byte[] bmsg) {
		int offset = 0;
		
		for (Map.Entry<String, TeleElement> entry : mHeaderMsg.entrySet()) {
			TeleElement element = entry.getValue();
			byte[] temp = new byte[element.getLength()];
			System.arraycopy(bmsg, offset, temp, 0, element.getLength());
			element.setData(temp);

			offset += element.getLength();
		}
		

		for (Map.Entry<String, TeleElement> entry : mBodyMsg.entrySet()) {
			TeleElement element = entry.getValue();
			byte[] temp = new byte[element.getLength()];
			System.arraycopy(bmsg, offset, temp, 0, element.getLength());
			element.setData(temp);

			offset += element.getLength();
		}
		//printMsg();
		
		//execute();
		
		//printMsg();
	}
	
	
		
	public Object getHeaderElementData(String key) {
		TeleElement element = mHeaderMsg.get(key);
		
		if (element == null) return null;
		
		if (element.strMsgType.equals("B")) {
	        if (element.data != null && element.data.length == 1) {
	            // 단일 byte일 경우, Byte 객체 반환
	            return element.data[0]; // auto-boxing → Byte
	        }
	        return element.data; // byte[] 그대로 반환
	    } else {
	        return element.getStringData(); // 문자열 반환
	    }
		
	}
	
	/**
	 * 바디 값 설정 (String, byte, byte[], int 지원)
	 * @param key 바디 키값
	 * @param value 설정할 값
	 */
	public void setBodyElementData(String key, Object value) {
	    TeleElement element = mBodyMsg.get(key);

	    if (element != null) {
	        int len = element.getLength();

	        if (value instanceof Byte) {
	            element.setData(new byte[]{(Byte) value});
	        } else if (value instanceof byte[]) {
	            element.setData((byte[]) value);
	        } else if (value instanceof String) {
	            element.setData(DmetaUtil.lPadString((String) value, len).getBytes());
	        } else if (value instanceof Integer) {
	            int intValue = (Integer) value;
	            byte[] bytes = new byte[len];
	            for (int i = 0; i < len; i++) {
	                bytes[len - 1 - i] = (byte) ((intValue >> (i * 8)) & 0xFF);
	            }
	            element.setData(bytes);
	        } else {
	            throw new IllegalArgumentException("지원하지 않는 데이터 타입입니다: " + value.getClass());
	        }
	    }
	}

	
	/**
	 * 헤더 값 설정
	 * @param key 헤더 키값
	 * @param value 설정할 값 (byte[], String, int, byte)
	 */
	public void setHeaderElementData(String key, Object value) {
	    TeleElement element = mHeaderMsg.get(key);
	    int len = element.getLength(); // TeleElement에 설정된 길이만큼
	    
	    if (element != null) {
	        if (value instanceof Byte) {
	            element.setData(new byte[]{(Byte) value});
	        } else if (value instanceof byte[]) {
	            element.setData((byte[]) value);
	        } else if (value instanceof String) {
	            element.setData(( DmetaUtil.lPadString((String) value,len)).getBytes());
	            
	        } else if (value instanceof Integer) {
	        	int intValue = (Integer) value;
	        	
	        	byte[] bytes = new byte[len];
        	    
        	    for (int i = 0; i < len; i++) {
        	        bytes[len - 1 - i] = (byte) ((intValue >> (i * 8)) & 0xFF);
        	    }
        	    element.setData(bytes);
        	    
	        } else {
	            throw new IllegalArgumentException("지원하지 않는 데이터 타입입니다: " + value.getClass());
	        }
	    }
	}
    
	/**
	 * 길이부 가져오는 함수
	 * */
	public int getToTalLen() {
		
		int rnum = 0;
		
		for(String headerKey :mHeaderMsg.keySet())
			rnum += mHeaderMsg.get(headerKey).nLen;
			
		for(String bodyKey : mBodyMsg.keySet())
			rnum += mBodyMsg.get(bodyKey).nLen;
		
		
		return rnum;
	}
	
	public int getBodyLen() {
		
		int rnum = 0;
			
		for(String bodyKey : mBodyMsg.keySet())
			rnum += mBodyMsg.get(bodyKey).nLen;
		
		return rnum;
	}
	
	public int getHeaderLen() {
		
		int rnum = 0;
			
		for(String headerKey :mHeaderMsg.keySet())
			rnum += mHeaderMsg.get(headerKey).nLen;
		
		return rnum;
	}

	public void execute() {
		System.out.println("=== TeleService: 실행 시작 ===");
		startSvrMain();
		System.out.println("=== TeleService: 실행 완료 ===");
		
		System.out.println("=== SendMsg : 실행  ===");
		getSendMsg();
		System.out.println("=== SendMsg : 실행  ===");
		
		printMsg();
	}

	

}
