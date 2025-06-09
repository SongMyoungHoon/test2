package test.com.dmeta.struct;

public class Test2 {

	
	    private static final int POLYNOMIAL = 0x8005;  // CRC-16 다항식
	    private static final int INITIAL_VALUE = 0xFFFF;  // 초기값

	    public static int calculateCRC16(byte[] data) {
	        int crc = INITIAL_VALUE;  // CRC 초기값 설정

	        for (byte b : data) {  // 모든 프레임 데이터 순차적 처리
	            crc ^= (b & 0xFF);  // XOR 연산
	            for (int i = 0; i < 8; i++) {  // 8비트 반복
	                if ((crc & 0x0001) != 0) {  // 최하위 비트가 1이면 다항식 XOR
	                    crc = (crc >> 1) ^ POLYNOMIAL;
	                } else {
	                    crc >>= 1;
	                }
	            }
	        }
	        return crc & 0xFFFF;  // 16비트 값 반환
	    }

	    public static void main(String[] args) {
	        // 9개 프레임 데이터를 합치기
	        byte[] frame1 = {0x01};  // 1바이트
	        byte[] frame2 = {0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};  // 8바이트
	        byte[] frame3 = new byte[900];  // 900바이트 (예제 데이터: 0xAA로 채움)
	        for (int i = 0; i < frame3.length; i++) {
	            frame3[i] = (byte) 0xAA;
	        }

	        // 전체 데이터를 하나의 배열로 합치기
	        int totalSize = frame1.length + frame2.length + frame3.length;
	        byte[] fullData = new byte[totalSize];

	        int index = 0;
	        System.arraycopy(frame1, 0, fullData, index, frame1.length);
	        index += frame1.length;
	        System.arraycopy(frame2, 0, fullData, index, frame2.length);
	        index += frame2.length;
	        System.arraycopy(frame3, 0, fullData, index, frame3.length);

	        // 9개 프레임(909바이트) 전체를 이용하여 CRC-16 계산
	        int crcResult = calculateCRC16(fullData);

	        // LOW BYTE, HIGH BYTE 분리
	        byte lowByte = (byte) (crcResult & 0xFF);       // 0xF1 (하위 8비트)
	        byte highByte = (byte) ((crcResult >> 8) & 0xFF); // 0xB8 (상위 8비트)

	        // LOW BYTE 먼저, HIGH BYTE 나중에 저장 (프레임10 역할)
	        byte[] frame10 = {lowByte, highByte};

	        // 출력 확인
	        System.out.printf("CRC-16 결과 (909바이트 전체): 0x%04X%n", crcResult);
	        System.out.printf("프레임10 (LOW BYTE, HIGH BYTE): 0x%02X 0x%02X%n", frame10[0], frame10[1]);
	    }
}
