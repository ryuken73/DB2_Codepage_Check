BEGIN{
    INSERT_SQL="insert into cp949 values(X'%s');";
}
# select between <tr></tr> only
/^<tr>/,/<\/tr>$/{
    if(!/^<td>/){
        next;
    }
    elementValue = rmTag("td", $0);
    if(/^<td>[0-9A-Z]{3}<\/td>/){
        # start of new base.
        baseIndex = NR;
        base = elementValue;
        next;
    }
    hexIndex = toHex(NR-baseIndex-1);
    hexCode = base hexIndex;
    if(base != ""){
        print NR, base, subIndex, hexCode, elementValue, $0;
        sql = genSQL(hexCode, INSERT_SQL);
        print sql;
    }
}
END {
    print "total number of char =" count;
}

function rmTag(tag, element){
    gsub(/[<>\\/]/, "", element);
    gsub(tag, "", element);
    return element;
}
function genSQL(code, sql){
    sqlStatement = sprintf(sql,code);
    return sqlStatement;
}

function toHex(number){
    return toupper(sprintf("%x", number));
}