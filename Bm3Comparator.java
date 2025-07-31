package com.fisglobal.taxreporting.entity.model.taxreporting.bsb;

import java.io.Serializable;
import java.util.Comparator;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;


public class Bm3Comparator implements Comparator<TrTableBm3>, Serializable {
    @Override
    public int compare(TrTableBm3 o1, TrTableBm3 o2) {
        long t1_time = o1.getTrTableBm3PK().getKeySysDatum() * 1_000_000 + o1.getTrTableBm3PK().getKeyUhrzeit();
        long t2_time = o2.getTrTableBm3PK().getKeySysDatum() * 1_000_000 + o2.getTrTableBm3PK().getKeyUhrzeit();
        if (t1_time != t2_time) {
            return Long.compare(t1_time, t2_time);
        }
        String t1 = o1.getTrTableBm3PK().getKeySatzart();
        String t2 = o2.getTrTableBm3PK().getKeySatzart();
        if (t1.equals(t2)) {
            return 0;
        }
        if (t1.equals("H")) {
            return -1;
        }
        if (t2.equals("H")) {
            return 1;
        }
        if (t1.equals("B") && t2.equals("K")) {
            return -1;
        }
        if (t1.equals("K") && t2.equals("B")) {
            return 1;
        }
        if (t1.equals("B") && t2.equals("S")) {
            return -1;
        }
        if (t1.equals("S") && t2.equals("B")) {
            return 1;
        }
        if (t1.equals("K") && t2.equals("S")) {
            return -1;
        }
        if (t1.equals("S") && t2.equals("K")) {
            return 1;
        }

        return 0;
    }
}
