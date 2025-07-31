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
public class CreationFields {
    @Autowired
    private UserNameProvider userNameProvider;

    @Autowired
    private CurrentTimeProvider timeProvider;

    public int update(TrTableBsb dataToUpdate) {
        int programmaticallyUpdated = 0;

        if (dataToUpdate.getAnlageDatum() == null) {
            programmaticallyUpdated++;
            dataToUpdate.setAnlageDatum(new BigDecimal(getCurrentTime("yyyyMMdd")));
        } else {
            if (!dataToUpdate.getAnlageDatum().equals(getCurrentTime("yyyyMMdd"))) {
                programmaticallyUpdated++;
                dataToUpdate.setAnlageDatum(new BigDecimal(getCurrentTime("yyyyMMdd")));
            }
        }

        if (dataToUpdate.getAnlageZeit() == null) {
            programmaticallyUpdated++;
            dataToUpdate.setAnlageZeit(new BigDecimal(getCurrentTime("HHmmss")));
        } else {
            if (!dataToUpdate.getAnlageZeit().equals(getCurrentTime("HHmmss"))) {
                programmaticallyUpdated++;
                dataToUpdate.setAnlageZeit(new BigDecimal(getCurrentTime("HHmmss")));
            }
        }

        if (dataToUpdate.getAnlageErfasser() == null) {
            programmaticallyUpdated++;
            dataToUpdate.setAnlageErfasser(userNameProvider.getAuthenticatedUserName());
        } else {
            if (!dataToUpdate.getAnlageErfasser().equals(userNameProvider.getAuthenticatedUserName())) {
                programmaticallyUpdated++;
                dataToUpdate.setAnlageErfasser(userNameProvider.getAuthenticatedUserName());
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

        return programmaticallyUpdated;
    }

    private String getCurrentTime(String format) {
        return timeProvider.getCurrentTime(format);
    }
}
