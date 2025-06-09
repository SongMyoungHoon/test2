package test.com.dmeta.struct.tele;

import java.util.LinkedHashMap;
import java.util.Map;

import test.com.dmeta.struct.TeleElement;

public class SendHeader {

	private Map<String, TeleElement> mHeaderyMsg = new LinkedHashMap<>();

	
	public SendHeader() {		
		
		addHeaderMsg( new TeleElement("TYPE"       ,"B" ,1  ));
		addHeaderMsg( new TeleElement("SEQ"        ,"B" ,2  ));
		addHeaderMsg( new TeleElement("DATE"       ,"S" ,14 ));
		addHeaderMsg( new TeleElement("FIELD_TYPE" ,"B" ,1  ));
		addHeaderMsg( new TeleElement("LINE"       ,"B" ,1  ));
		addHeaderMsg( new TeleElement("STATION_NO" ,"B" ,1  ));
		addHeaderMsg( new TeleElement("OP_CODE"    ,"B" ,1  ));
		addHeaderMsg( new TeleElement("TOTAL_LEN"  ,"B" ,2  ));
		
	}
	
	 public void addHeaderMsg(TeleElement tMsg) {
	    mHeaderyMsg.put(tMsg.getKey(), tMsg);
	 }

	 public Map<String, TeleElement> getHeaderMap() {
	    return mHeaderyMsg;
	 }

	
	
}
