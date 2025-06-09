package test.com.dmeta.struct;

public  class TeleElement {

	String strMsgKey  = "";
	String strMsgType = "";
	int    nLen       = 0 ;
	byte[] data       = null;
			
	public TeleElement(String strMsgKey, String strMsgType, int nLen ){
		
		this.strMsgKey  =strMsgKey;
		this.strMsgType = strMsgType;
		this.nLen       = nLen;
		this.data = new byte[nLen];
		
	}
	
	public String getKey() {
        return strMsgKey;
    }
	
	public String getType() {
        return strMsgType;
	}
	
	public int getLength() {
        return nLen;
    }
	
	public byte[] getData() {
	        return data;
	}
	
	
	 
	 public String getStringData() {
	        return new String(data);
	}
	
	public void setData(byte[] inputData) {
		if(strMsgType.equals("B")) {
			for(int i = 0; i<this.nLen ; i++ )
				this.data[i] = inputData[i];
		}	
		else
			System.arraycopy(inputData, 0, this.data, 0, this.nLen);
    }
	
	
}
