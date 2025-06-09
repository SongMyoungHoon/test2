package test.com.dmeta.util;

public class DmetaUtil {

	/** byte 배열을 unsigned int로 변환 (1~4 바이트 지원)
    * @param bytes 변환할 byte 배열
    * @return 변환된 int 값
    * @throws IllegalArgumentException 유효하지 않은 배열 길이
    */
   public static int byteArrayToInt(byte[] bytes) {
       if (bytes == null || bytes.length == 0 || bytes.length > 4) {
           throw new IllegalArgumentException("배열 길이는 1~4 바이트여야 합니다.");
       }

       int result = 0;
       for (int i = 0; i < bytes.length; i++) {
           result |= (Byte.toUnsignedInt(bytes[i]) << (8 * (bytes.length - 1 - i)));
       }
       return result;
   }

   /**
    * int 값을 byte 배열로 변환 (1~4 바이트)
    * @param value 변환할 int 값
    * @param byteLength 변환할 바이트 길이 (1~4)
    * @return 변환된 byte 배열
    */
   public static byte[] intToByteArray(int value, int byteLength) {
       if (byteLength < 1 || byteLength > 4) {
           throw new IllegalArgumentException("바이트 길이는 1~4 바이트여야 합니다.");
       }

       byte[] bytes = new byte[byteLength];
       for (int i = 0; i < byteLength; i++) {
           bytes[i] = (byte) ((value >> (8 * (byteLength - 1 - i))) & 0xFF);
       }
       return bytes;
   }
   
   /**
    * Object 값을 unsigned int로 변환
    * @param obj 변환할 Object (byte[], Byte, Integer, Long 등 지원)
    * @return 변환된 int 값
    * @throws IllegalArgumentException 지원되지 않는 타입일 경우 예외 발생
    */
   public static int objectToInt(Object obj) {
       if (obj == null) {
           throw new IllegalArgumentException("Object가 null입니다.");
       }

       if (obj instanceof Byte) {
           return Byte.toUnsignedInt((Byte) obj);
       }

       if (obj instanceof byte[]) {
           byte[] bytes = (byte[]) obj;
           if (bytes.length == 1) {
               return Byte.toUnsignedInt(bytes[0]);
           } else if (bytes.length <= 4) {
               return DmetaUtil.byteArrayToInt(bytes);
           }
           throw new IllegalArgumentException("byte 배열은 최대 4바이트까지만 지원됩니다.");
       }

       if (obj instanceof Integer) {
           return (Integer) obj;
       }

       if (obj instanceof Long) {
           long value = (Long) obj;
           if (value < 0 || value > 0xFFFFFFFFL) {
               throw new IllegalArgumentException("Long 값이 unsigned int 범위를 초과했습니다.");
           }
           return (int) value;
       }

       throw new IllegalArgumentException("지원되지 않는 타입: " + obj.getClass().getSimpleName());
   }
   
   /**
    * 문자열을 지정된 길이만큼 오른쪽('0') 패딩
    * @param input 원본 문자열
    * @param length 총 길이
    * @return 오른쪽 0이 추가된 문자열
    */
   public static String rPadNumber(String input, int length) {
       if (input == null) input = "";
       if (input.length() >= length) return input.substring(0, length);
       return String.format("%-" + length + "s", input).replace(' ', '0');
   }

   /**
    * 문자열을 지정된 길이만큼 왼쪽(스페이스) 패딩
    * @param input 원본 문자열
    * @param length 총 길이
    * @return 왼쪽 공백이 추가된 문자열
    */
   public static String lPadString(String input, int length) {
       if (input == null) input = "";
       if (input.length() >= length) return input.substring(0, length);
       return String.format("%" + length + "s", input);
   }
}
