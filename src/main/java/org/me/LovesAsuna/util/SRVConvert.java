package org.me.LovesAsuna.util;

import org.xbill.DNS.*;

public class SRVConvert {
    public static String convert(String host) throws TextParseException {
        int port = 0;
        Record[] records = new Lookup( "_minecraft._tcp." + host, Type.SRV).run();
        if (records != null && records.length > 0) {
            SRVRecord result  = (SRVRecord) records[0];
            host = result.getTarget().toString().replaceFirst( "\\.$", "");
            port = result.getPort();
            return (host + ":" + port);
        } else {
            return null;
        }
    }
}
