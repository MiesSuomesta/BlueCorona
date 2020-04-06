package com.lja.bluecorona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class btScanRecordParser {

    public btScanRecordParser() {}

    public List<btScanRecord> parseScanRecord(byte[] scanRecord) {
        List<btScanRecord> records = new ArrayList<btScanRecord>();

        int index = 0;
        while (index < scanRecord.length) {
            int length = scanRecord[index++];
            //Done once we run out of records
            if (length == 0) break;

            int type = scanRecord[index];
            //Done if our record isn't a valid type
            if (type == 0) break;

            byte[] data = Arrays.copyOfRange(scanRecord, index+1, index+length);

            records.add(new btScanRecord(length, type, data));
            //Advance
            index += length;
        }

        return records;
    }

}
