package com.fisglobal.taxreporting.util.journaling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class hold journaling object with respective status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BsbPairWithChangedFlag {
    boolean changed;
    BsbPair pair;
}
