import java.nio.charset.Charset;
import java.nio.charset.*;
import java.sql.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import javax.xml.bind.DatatypeConverter;

public class SelectDB_ERR
{
  public static String hexToString(String codepage, String hexString)
  {
    Charset charset = Charset.forName(codepage);
    CharsetDecoder decoder = charset.newDecoder();
    try {
      byte[] bytes = DatatypeConverter.parseHexBinary(hexString);
      ByteBuffer buff = ByteBuffer.wrap(bytes);
      CharBuffer cbuf = decoder.decode(buff);
      String s = cbuf.toString();
      return s;
    }
    catch (CharacterCodingException e){
      System.out.println("Exception: " + e);
      e.printStackTrace();
      String errStr = "E";
      return errStr;
    }
    catch (Exception e){
      System.out.println("Exception: " + e);
      e.printStackTrace();
      String errStr = "Unkown err";
      return errStr;
    }
  }

  public static void main(String[] args)
  {
    String urlPrefix = "jdbc:db2:";
    String url;
    String user;
    String password;
    String str1;
    String hexValue;
    String str3;
    String defaultCharset;
    Connection con;
    Statement stmt;
    ResultSet rs;

    System.out.println ("**** Enter class EzJava");

    // Check the that first argument has the correct form for the portion
    // of the URL that follows jdbc:db2:,
    // as described
    // in the Connecting to a data source using the DriverManager
    // interface with the IBM Data Server Driver for JDBC and SQLJ topic.
    // For example, for IBM Data Server Driver for
    // JDBC and SQLJ type 2 connectivity,
    // args[0] might be MVS1DB2M. For
    // type 4 connectivity, args[0] might
    // be //stlmvs1:10110/MVS1DB2M.

    if (args.length!=3)
    {
      System.err.println ("Invalid value. First argument appended to "+
       "jdbc:db2: must specify a valid URL.");
      System.err.println ("Second argument must be a valid user ID.");
      System.err.println ("Third argument must be the password for the user ID.");
      System.exit(1);
    }
    url = urlPrefix + args[0];
    user = args[1];
    password = args[2];
    try
    {
      // Load the driver
      Class.forName("com.ibm.db2.jcc.DB2Driver");
      System.out.println("**** Loaded the JDBC driver");

      defaultCharset = Charset.defaultCharset().displayName();
      System.out.println("Charset.defaultCharset().displayName() = "+defaultCharset);

      // Create the connection using the IBM Data Server Driver for JDBC and SQLJ
      con = DriverManager.getConnection (url, user, password);
      // Commit changes manually
      con.setAutoCommit(false);
      System.out.println("**** Created a JDBC connection to the data source");

      // Create the Statement
      stmt = con.createStatement();
      System.out.println("**** Created JDBC Statement object");

      // Execute a query and generate a ResultSet instance
      rs = stmt.executeQuery("select a,hex(a) from nrsinst.euckr");
      // rs = stmt.executeQuery("select binary(a),hex(a) from nrsinst.euckr");
      System.out.println("**** Created JDBC ResultSet object");

      Charset charset = Charset.forName("MS949");
      CharsetDecoder decoder = charset.newDecoder();

      // Print all of the employee numbers to standard output device
      while (rs.next()) {
        str1 = rs.getString(1);
        hexValue = rs.getString(2);
        String stringMS949 = hexToString("MS949", hexValue);
        System.out.println("value = " + str1 + " , hex = " + hexValue + ", MS949 = " + stringMS949 );
      }
      System.out.println("**** Fetched all rows from JDBC ResultSet");
      // Close the ResultSet
      rs.close();
      System.out.println("**** Closed JDBC ResultSet");

      // Close the Statement
      stmt.close();
      System.out.println("**** Closed JDBC Statement");

      // Connection must be on a unit-of-work boundary to allow close
      con.commit();
      System.out.println ( "**** Transaction committed" );

      // Close the connection
      con.close();
      System.out.println("**** Disconnected from data source");

      System.out.println("**** JDBC Exit from class EzJava - no errors");

    }

    catch (ClassNotFoundException e)
    {
      System.err.println("Could not load JDBC driver");
      System.out.println("Exception: " + e);
      e.printStackTrace();
    }

    catch(SQLException ex)
    {
      System.err.println("SQLException information");
      while(ex!=null) {
        System.err.println ("Error msg: " + ex.getMessage());
        System.err.println ("SQLSTATE: " + ex.getSQLState());
        System.err.println ("Error code: " + ex.getErrorCode());
        ex.printStackTrace();
        ex = ex.getNextException(); // For drivers that support chained exceptions
      }
    }

    catch(Exception ex){
        System.err.println ("Error msg: " + ex.getMessage());
        ex.printStackTrace();
    }
  }
}