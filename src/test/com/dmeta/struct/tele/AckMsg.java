package test.com.dmeta.struct.tele;

import java.util.LinkedHashMap;
import java.util.Map;

import test.com.dmeta.struct.TeleElement;
import test.com.dmeta.struct.TeleService;

public class AckMsg extends TeleService{

	private Map<String, TeleElement> mAckMsg = new LinkedHashMap<>();
	
	public AckMsg(){
		addBodyMsg( new TeleElement("TYPE"         ,"B" ,1  ));
		addBodyMsg( new TeleElement("SEQ"          ,"B" ,2  ));
		addBodyMsg( new TeleElement("DATE"         ,"S" ,14 ));
		addBodyMsg( new TeleElement("TOTAL_LEN"    ,"B" ,4  ));
		addBodyMsg( new TeleElement("RET"          ,"B" ,1  ));
	}

		@Override
		protected void startSvrMain() {
			// TODO Auto-generated method stub
			
			
			
		}


		@Override
		protected void getSendMsg() {
			// TODO Auto-generated method stub
			
		}


	
}
