package test.com.dmeta.struct.constant;

public class Constant {

	//DATA TYPE
	public static final byte DATA_SEND        =       0x01 ; //(1)데이터 전송
	public static final byte DATA_SEND_ACK    =       0x02 ; //(2)데이터 전송 ACK
	public static final byte EVENT_SEND       =       0x03 ; //(3)이벤트 전송
	public static final byte EVENT_SEND_ACK   =       0x04 ; //(4)이벤트 전송 ACK
	public static final byte KEEP_ALIVE       = (byte)0xFE ; //(5) Keep Alive
	public static final byte KEEP_ALIVE_ACK   = (byte)0xFF ; //(6) Keep Alive ACK
	
	//분야 FIELD_TYPE
	public static final byte SIGNAL_TYPE      =       0x01 ; //1)신호분야
	public static final byte POWER_TYPE       =       0x02 ; //(2)전력분야
	public static final byte COMM_TYPE        =       0x03 ; //(3)통신분야
	public static final byte MECH_TYPE        =       0x04 ; //(4)기계분야
	public static final byte PSD_TYPE         =       0x05 ; //(5) PSD분야
	public static final byte INSPECT_TYPE     =       0x06 ; //(6) 차량검수분야
	

	
}
