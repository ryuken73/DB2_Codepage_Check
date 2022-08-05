# DB2 970 코드페이지에서의 한글 입출력 테스트 스크립트
## MS949 한글 insert 스크립트 생성
```
awk/awk -f cp949.awk > insert_cp949_hangul.clp
```
## 970 코드페이지 DB에서 JDBC로 한글 읽기
```
run SelectDB.bat or run_SelectDB.sh
```
단, JDBC url(IP, Port, DBname)과 Id/pwd 수정해야함.  

## HEX -> 코드페이지에 맞는 한글 출력
```
runDecodeEncode.bat
```
결과
```
"c:/Program Files/ojdkbuild/java-1.8.0-openjdk-1.8.0.222-2/bin/java" DecodeEncode B0A1 MS949
**** Decode and Encode string:B0A1 by charset MS949
**** Decoded = [가]
```
 
### 아래 오류에 대한 조사를 위해 만든 스크립트들
```
SQLException information
Error msg: [jcc][t4][1065][12306][3.69.76] java.io.CharConversionException이 발생했습니다. 세부사항은 첨부한 Throwable을 참조하십시오. ERRORCODE=-4220, SQLSTATE=null
SQLSTATE: null
Error code: -4220
com.ibm.db2.jcc.am.SqlException: [jcc][t4][1065][12306][3.69.76] java.io.CharConversionException이 발생했습니다. 세부사항은 첨부한 Throwable을 참조하십시오. ERRORCODE=-4220, SQLSTATE=null
        at com.ibm.db2.jcc.am.b0.a(b0.java:733)
        at com.ibm.db2.jcc.am.b0.a(b0.java:66)
        at com.ibm.db2.jcc.am.b0.a(b0.java:120)
        at com.ibm.db2.jcc.am.be.a(be.java:2868)
        at com.ibm.db2.jcc.am.be.p(be.java:525)
        at com.ibm.db2.jcc.am.be.P(be.java:1604)
        at com.ibm.db2.jcc.am.ResultSet.getStringX(ResultSet.java:1147)
        at com.ibm.db2.jcc.am.ResultSet.getString(ResultSet.java:1122)
        at SelectDB_ERR.main(SelectDB_ERR.java:102)
Caused by: java.nio.charset.MalformedInputException: Input length = 1
        at java.nio.charset.CoderResult.throwException(CoderResult.java:281)
        at com.ibm.db2.jcc.am.v.a(v.java:52)
        at com.ibm.db2.jcc.am.be.a(be.java:2864)
        ... 5 more
``들