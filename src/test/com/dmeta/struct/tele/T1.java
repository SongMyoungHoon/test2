package test.com.dmeta.struct.tele;

import java.util.HashMap;
import java.util.Map;

import test.com.dmeta.struct.TeleElement;
import test.com.dmeta.struct.TeleService;

public class T1 extends TeleService{

	public T1(){
		
		
		//addBodyMsg( new TeleElement("TYPE","B",1));
		addBodyMsg( new TeleElement("T1","S",1));
		addBodyMsg( new TeleElement("T2","S",2));
		addBodyMsg( new TeleElement("T3","S",3));
		
		addBodyMsg(new TeleElement("T4","R",0));
			addsubBodyMsg(new TeleElement("T4_1","S",4));
			addsubBodyMsg(new TeleElement("T4_2","S",2));
		
		
		initSendHeader();
	}

	@Override
	protected void startSvrMain() {
		
		
		
		// TODO Auto-generated method stub
		System.out.println("START processData");
		
		//System.out.println(getElementData("T1"));
		
		//System.out.println( getHeaderElementData("SEQ"));
		
		//byte[] bb = (byte[]) getHeaderElementData("SEQ");
		 
		//System.out.println(bb.length);
		
		
		//byte cc = 0X01;
		
		//setHeaderElementData("LINE" , cc);
		
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("T1", "T");
		map.put("T2", "T");
		map.put("T3", "T");
		
		for(String key : map.keySet())
			setBodyElementData(key,  String.valueOf(map.get(key)));
		
		addBodyMsg( new TeleElement("T4","S",5));
		
		setBodyElementData("T4",  "ppppp");
		
		setDataHeader(getBodyData());
		setHeaderElementData("FIELD_TYPE", 1);
		setHeaderElementData("LINE"      , 9);
		setHeaderElementData("STATION_NO", 1);
		setHeaderElementData("OP_CODE"   , 36);
		
		
		
		if((byte)getHeaderElementData("LINE")==0x09) {
			System.out.println("LINE" + "09");
		}
		else {
			System.out.println("(byte)getHeaderElementData(LINE)" + (byte)getHeaderElementData("STATION_NO"));
		}
		
	}

	@Override
	protected void getSendMsg() {
		// TODO Auto-generated method stub
		System.out.println("-----"+ getToTalLen());
		
	}
	
	
	
}
