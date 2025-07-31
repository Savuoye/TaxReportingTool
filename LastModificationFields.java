package com.fisglobal.taxreporting.util;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.service.infrastructure.CurrentTimeProvider;
import com.fisglobal.taxreporting.service.infrastructure.UserNameProvider;


/**
 * This class programmatically updated last modified user and date time details
 */
@Component
public class LastModificationFields {
    @Autowired
    private UserNameProvider userNameProvider;

    @Autowired
    private CurrentTimeProvider timeProvider;

    public int update(TrTableBsb dataToUpdate) {
        int programmaticallyUpdated = 0;
        if (dataToUpdate.getAenderZeit() == null) {
            programmaticallyUpdated++;
            dataToUpdate.setAenderZeit(new BigDecimal(getCurrentTime("HHmmss")));
        } else {
            if (dataToUpdate.getAenderZeit().compareTo(new BigDecimal(getCurrentTime("HHmmss"))) != 0) {
                programmaticallyUpdated++;
                dataToUpdate.setAenderZeit(new BigDecimal(getCurrentTime("HHmmss")));
            }
        }

        if (dataToUpdate.getAenderDatum() == null) {
            programmaticallyUpdated++;
            dataToUpdate.setAenderDatum(new BigDecimal(getCurrentTime("yyyyMMdd")));
        } else {
            if (dataToUpdate.getAenderDatum().compareTo(new BigDecimal(getCurrentTime("yyyyMMdd"))) != 0) {
                programmaticallyUpdated++;
                dataToUpdate.setAenderDatum(new BigDecimal(getCurrentTime("yyyyMMdd")));
            }
        }

        if (dataToUpdate.getBedbsbTimestamp() == null) {
            programmaticallyUpdated++;
            dataToUpdate.setBedbsbTimestamp(getCurrentTime("yyyyMMddHHmmssSS"));
        } else {
            if (!dataToUpdate.getBedbsbTimestamp().equals(getCurrentTime("yyyyMMddHHmmssSS"))) {
                programmaticallyUpdated++;
                dataToUpdate.setBedbsbTimestamp(getCurrentTime("yyyyMMddHHmmssSS"));
            }
        }
        if (dataToUpdate.getAenderErfasser() == null) {
            programmaticallyUpdated++;
            dataToUpdate.setAenderErfasser(userNameProvider.getAuthenticatedUserName());
        } else {
            if (!dataToUpdate.getAenderErfasser().trim().equals(userNameProvider.getAuthenticatedUserName().trim())) {
                programmaticallyUpdated++;
                dataToUpdate.setAenderErfasser(userNameProvider.getAuthenticatedUserName());
            }
        }
        return programmaticallyUpdated;
    }

    public BigDecimal getCurrentAenderDatum(BigDecimal aenderDatum) {
        return new BigDecimal(getCurrentTime("yyyyMMdd"));
    }

    public BigDecimal getCurrentAenderZeit(BigDecimal aenderZeit) {
        return new BigDecimal(getCurrentTime("HHmmss"));
    }

    public String getAenderErfasser(String aenderErfasser) {
        return userNameProvider.getAuthenticatedUserName();
    }

    public String getBedbsbTimestamp(String bedbsbTimestamp) {
        return getCurrentTime("yyyyMMddHHmmssSS");
    }

    private String getCurrentTime(String format) {
        return timeProvider.getCurrentTime(format);
    }
}
