import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import javax.xml.bind.DatatypeConverter;

public class DecodeEncode
{
  public static ByteBuffer hexStringToBuffer(String hex) {
    ByteBuffer buff = ByteBuffer.allocate(hex.length()/2);
    for (int i = 0; i < hex.length(); i+=2) {
        buff.put((byte)Integer.parseInt(hex.substring(i, i+2), 16));
    }
    buff.rewind();
    return buff;
  }
  public static void main(String[] args)
  {
    String paramCharset;
    String paramHex;
    paramHex = args[0];
    paramCharset = args[1];

    System.out.println ("**** Decode and Encode string:"+paramHex+" by charset "+paramCharset);
    Charset charset = Charset.forName(paramCharset);
    CharsetEncoder encoder = charset.newEncoder();
    CharsetDecoder decoder = charset.newDecoder();
    try {
      //byte[] bytes = hexStringToByteArray(paramHex);
      //String s = new String(bytes, paramCharset);
      //ByteBuffer buff = encoder.encode(CharBuffer.wrap(bytes));
      ByteBuffer buff = hexStringToBuffer(paramHex);
      CharBuffer cbuf = decoder.decode(buff);
      String s = cbuf.toString();
      System.out.println ("**** Decoded = ["+s+"]");
    }
    catch(CharacterCodingException e){
      System.out.println("Exception: " + e);
      e.printStackTrace();
    }
  }
}
