package com.fisglobal.taxreporting.service.taxreportingcorrection.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableAamPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm1Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableAkbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm1Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm3Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableAkePK;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm1Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm3Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1PK;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3PK;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm1Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm3Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableEikPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm1Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm3Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTablePivPK;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.BusinessValidation;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.infrastructure.CurrentTimeProvider;
import com.fisglobal.taxreporting.service.infrastructure.UserNameProvider;
import com.fisglobal.taxreporting.service.taxreportingcorrection.TaxReportingCorrectionService;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingjournaling.TaxReportingJournalingService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.LastModificationFields;
import com.fisglobal.taxreporting.util.MapDifferentFieldsExceptKeys;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Tax reporting correction class will implemented to update the tax report
 * record
 */
@Service
public class TaxReportingCorrectionServiceImpl implements TaxReportingCorrectionService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingCorrectionServiceImpl.class);

    @Autowired
    public TaxReportingRepository taxReportingRepository;

    @Autowired
    public UserNameProvider userNameProvider;

    @Autowired
    public CurrentTimeProvider timeProvider;

    @Autowired
    private LastModificationFields lastModificationFields;

    @Autowired
    private TaxReportingJournalingService taxReportingJournalingService;

    @Autowired
    private TaxReportingResponseService taxReportingResponseService;

    @Autowired
    private TaxReportingDetailService taxReportingDetailService;

    MapDifferentFieldsExceptKeys dataMapper = new MapDifferentFieldsExceptKeys();

    protected TrTableBsb updatedTableBsb = null;

    @SuppressWarnings("unused")
    public ValidationResults updateTaxReportingData(TrTableBsb frontendBsb, String action)
            throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Enter into update service for BSB: {}, Action: {}",
                frontendBsb.taxCertificateInfo(), action);

        int AbrechNr, maxAbrechNr, nextKeyAbrechNr;

        TrTableBsb originalDatabaseBsb = new TrTableBsb();

        TrTableBsb databaseBsb = taxReportingDetailService.fetchTrTableBsb(frontendBsb.getTrTableBsbPK().getMandSl(),
                frontendBsb.getTrTableBsbPK().getKeyIdNr(), frontendBsb.getTrTableBsbPK().getKeyMeldejahr(),
                frontendBsb.getTrTableBsbPK().getKeyMuster(), frontendBsb.getTrTableBsbPK().getKeyLaufnummer(),
                frontendBsb.getTrTableBsbPK().getKeySysDatum(), frontendBsb.getTrTableBsbPK().getKeyUhrzeit());

        databaseBsb.getTrTableBm1Set().stream().forEach(bm1 -> {
            Hibernate.initialize(bm1.getTrTableBm1AamSet());
            Hibernate.initialize(bm1.getTrTableBm1AkbSet());
            Hibernate.initialize(bm1.getTrTableBm1AkeSet());
            Hibernate.initialize(bm1.getTrTableBm1EikSet());
            Hibernate.initialize(bm1.getTrTableBm1PivSet());
        });
        databaseBsb.getTrTableBm3Set().stream().forEach(bm3 -> {
            Hibernate.initialize(bm3.getTrTableBm3AamSet());
            Hibernate.initialize(bm3.getTrTableBm3AkbSet());
            Hibernate.initialize(bm3.getTrTableBm3AkeSet());
            Hibernate.initialize(bm3.getTrTableBm3EikSet());
            Hibernate.initialize(bm3.getTrTableBm3PivSet());
        });

        originalDatabaseBsb = databaseBsb;

        Map<String, Object> journalingBeforeBsb = taxReportingJournalingService
                .journalingProcessInitializationBSB(originalDatabaseBsb);

        if (ObjectUtils.isEmpty(databaseBsb)) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_101,
                    ValidationMessage.ERROR_CODE_101_DESC);
        }

        if (CommonConstants.UPDATE_ACTION.equals(action)) {
            String[] validMeldeStatuses = { "00" };
            if (!Arrays.asList(validMeldeStatuses).contains(frontendBsb.getMeldeStatus())) {
                BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_105,
                        ValidationMessage.ERROR_CODE_105_DESC);
            }

            ReadOnly readOnly = new ReadOnly();
            boolean handledReadonly = readOnly.apply(frontendBsb, databaseBsb);
            if (!handledReadonly) {
                ValidationResults validationResults = new ValidationResults();
                validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_105,
                        ResponseMessage.MSG_CODE_105_DESC, String.valueOf(-1));
                return validationResults;
            }
        }

        if (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)) {
            HistoricalReadOnly historicalReadOnly = new HistoricalReadOnly();
            historicalReadOnly.apply(frontendBsb, databaseBsb);
        }

        Map<TrTableBm1PK, Set<TrTableAamPK>> aambm1PKFromFrontend = new HashMap<>();

        Map<TrTableBm1PK, Set<TrTableAkePK>> akebm1PKFromFrontend = new HashMap<>();

        Map<TrTableBm1PK, Set<TrTableEikPK>> eikbm1PKFromFrontend = new HashMap<>();

        Map<TrTableBm1PK, Set<TrTablePivPK>> aamPivPKFromFrontend = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTableAkbPK>> akbbm3PKFromFrontend = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTableAkePK>> akebm3PKFromFrontend = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTableEikPK>> eikbm3PKFromFrontend = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTablePivPK>> pivbm3PKFromFrontend = new HashMap<>();

        LOG.trace("[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Enter into frontend cache process ");

        cacheBm1AamPKFrontend(frontendBsb, aambm1PKFromFrontend);

        cacheBm1AkeFrontend(frontendBsb, akebm1PKFromFrontend);

        cacheBm1EikFrontend(frontendBsb, eikbm1PKFromFrontend);

        cacheBm1PivFrontend(frontendBsb, aamPivPKFromFrontend);

        cacheBm3AkbPKFrontend(frontendBsb, akbbm3PKFromFrontend);

        cacheBm3AkeFrontend(frontendBsb, akebm3PKFromFrontend);

        cacheBm3EikFrontend(frontendBsb, eikbm3PKFromFrontend);

        cacheBm3PivFrontend(frontendBsb, pivbm3PKFromFrontend);

        LOG.trace("[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Exit frontend cache process ");

        Map<TrTableBm1PK, Set<TrTableAamPK>> aambm1PKFromDatabase = new HashMap<>();
        Map<TrTableBm1PK, Set<TrTableBm1Aam>> bm1StorageSets = new HashMap<>();

        Map<TrTableBm1PK, Set<TrTableAkbPK>> akbbm1PKFromDatabase = new HashMap<>();
        Map<TrTableBm1PK, Set<TrTableBm1Akb>> bm1AkbStorageSets = new HashMap<>();

        Map<TrTableBm1PK, Set<TrTableAkePK>> akebm1PKFromDatabase = new HashMap<>();
        Map<TrTableBm1PK, Set<TrTableBm1Ake>> bm1AkeStorageSets = new HashMap<>();

        Map<TrTableBm1PK, Set<TrTableEikPK>> eikbm1PKFromDatabase = new HashMap<>();
        Map<TrTableBm1PK, Set<TrTableBm1Eik>> bm1EikStorageSets = new HashMap<>();

        Map<TrTableBm1PK, Set<TrTablePivPK>> pivbm1PKFromDatabase = new HashMap<>();
        Map<TrTableBm1PK, Set<TrTableBm1Piv>> bm1PivStorageSets = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTableAkbPK>> akbbm3PKFromDatabase = new HashMap<>();
        Map<TrTableBm3PK, Set<TrTableBm3Akb>> bm3StorageAkbSets = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTableAkePK>> akebm3PKFromDatabase = new HashMap<>();
        Map<TrTableBm3PK, Set<TrTableBm3Ake>> bm3StorageAkeSets = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTableEikPK>> eikbm3PKFromDatabase = new HashMap<>();
        Map<TrTableBm3PK, Set<TrTableBm3Eik>> bm3StorageEikSets = new HashMap<>();

        Map<TrTableBm3PK, Set<TrTablePivPK>> pivbm3PKFromDatabase = new HashMap<>();
        Map<TrTableBm3PK, Set<TrTableBm3Piv>> bm3StoragePivSets = new HashMap<>();

        LOG.trace("[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Enter into BSB DB cache process ");

        cacheBm1AamDatabase(databaseBsb, aambm1PKFromDatabase, bm1StorageSets);

        cacheBm1AkeDatabase(databaseBsb, akebm1PKFromDatabase, bm1AkeStorageSets);

        cacheBm1EikDatabase(databaseBsb, eikbm1PKFromDatabase, bm1EikStorageSets);

        cacheBm1PivDatabase(databaseBsb, pivbm1PKFromDatabase, bm1PivStorageSets);

        cacheBm3AkbDatabase(databaseBsb, akbbm3PKFromDatabase, bm3StorageAkbSets);

        cacheBm3AkeDatabase(databaseBsb, akebm3PKFromDatabase, bm3StorageAkeSets);

        cacheBm3EikDatabase(databaseBsb, eikbm3PKFromDatabase, bm3StorageEikSets);

        cacheBm3PivDatabase(databaseBsb, pivbm3PKFromDatabase, bm3StoragePivSets);

        LOG.trace("[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Exit BSB DB cache process ");

        LOG.trace(
                "[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Enter in to Subtables add and remove process ");

        int countModifications = calculateAdditionsAndRemovalsBm1Aam(frontendBsb, aambm1PKFromDatabase,
                aambm1PKFromFrontend, bm1StorageSets, action);

        countModifications += calculateAdditionsAndRemovalsBm1Ake(frontendBsb, akebm1PKFromDatabase,
                akebm1PKFromFrontend, bm1AkeStorageSets, action);

        countModifications += calculateAdditionsAndRemovalsBm1Eik(frontendBsb, eikbm1PKFromDatabase,
                eikbm1PKFromFrontend, bm1EikStorageSets, action);

        countModifications += calculateAdditionsAndRemovalsBm1Piv(frontendBsb, pivbm1PKFromDatabase,
                aamPivPKFromFrontend, bm1PivStorageSets, action);

        countModifications += calculateAdditionsAndRemovalsBm3Akb(frontendBsb, akbbm3PKFromDatabase,
                akbbm3PKFromFrontend, bm3StorageAkbSets, action);

        countModifications += calculateAdditionsAndRemovalsBm3Ake(frontendBsb, akebm3PKFromDatabase,
                akebm3PKFromFrontend, bm3StorageAkeSets, action);

        countModifications += calculateAdditionsAndRemovalsBm3Eik(frontendBsb, eikbm3PKFromDatabase,
                eikbm3PKFromFrontend, bm3StorageEikSets, action);

        countModifications += calculateAdditionsAndRemovalsBm3Piv(frontendBsb, pivbm3PKFromDatabase,
                pivbm3PKFromFrontend, bm3StoragePivSets, action);

        LOG.trace(
                "[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Exit Subtables add and remove process ");

        LOG.trace("[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Enter into update process ");

        countModifications += dataMapper.updateTaxReportingData(originalDatabaseBsb, frontendBsb, action);

        LOG.trace("[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Exit update process ");

        if (countModifications > 0) {

            LOG.trace(
                    "[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Enter in to saving updated field process ");

            countModifications += lastModificationFields.update(databaseBsb);
            taxReportingRepository.save(databaseBsb);
            updatedTableBsb = databaseBsb;
            if (action.equals(CommonConstants.UPDATE_ACTION)) {
                taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, databaseBsb,
                        CommonConstants.UPDATE_ACTION);
            } else {
                taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, databaseBsb,
                        CommonConstants.HISTORICAL_UPDATE_ACTION);
            }

            LOG.trace(
                    "[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Exit saving updated field process ");
        }

        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_109,
                ResponseMessage.MSG_CODE_109_DESC, String.valueOf(countModifications));

        LOG.trace(
                "[TaxReportingCorrectionServiceImpl] [updateTaxReportingData] Exit update service for BSB: {}, Action: {}",
                frontendBsb.taxCertificateInfo(), action);

        taxReportingResponseService.taxReportingCommonResponse(validationResults, databaseBsb);

        return validationResults;
    }

    private Integer calculateAdditionsAndRemovalsBm1Aam(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTableAamPK>> aambm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTableAamPK>> aambm1PKFromFrontend,
            Map<TrTableBm1PK, Set<TrTableBm1Aam>> bm1StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm1PK bm1PKFromDatabase : aambm1PKFromDatabase.keySet()) {
            if (aambm1PKFromFrontend.containsKey(bm1PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm1PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTableAamPK> aamPKFromFrontendSet = aambm1PKFromFrontend.get(bm1PKFromDatabase);
                Set<TrTableAamPK> aamPKFromDatabaseSet = aambm1PKFromDatabase.get(bm1PKFromDatabase);
                Set<TrTableBm1Aam> aamStorageReference = bm1StorageSets.get(bm1PKFromDatabase);

                Set<TrTableAamPK> aamPKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTableAamPK> pendingbm1Addition = new HashSet<>(aamPKFromFrontendSet);
                pendingbm1Addition.removeAll(aamPKFromDatabaseSet);
                Set<TrTableAamPK> pendingbm1Removal = new HashSet<>(aamPKFromDatabaseSet);
                pendingbm1Removal.removeAll(aamPKFromFrontendSet);

                maxAbrechNr = calculateMaximumAamPK(aamPKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm1.getTrTableBm1PK().getKeySatzart().equals("H")
                            || !frontendBm1.getTrTableBm1PK().getKeySatzart()
                                    .equals(bm1PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm1Aam frontendBm1Aam : frontendBm1.getTrTableBm1AamSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm1Aam.getTrTableAamPK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm1Aam.getTrTableAamPK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            aamStorageReference.add(frontendBm1Aam);
                        }
                        aamPKFrontendStorageReferenceSet.add(frontendBm1Aam.getTrTableAamPK());
                    }
                }

                if (aamPKFromDatabaseSet != null
                        && aamPKFrontendStorageReferenceSet.size() != aamPKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }
                if (countModification > 0) {
                    Map<TrTableAamPK, TrTableBm1Aam> aamFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm1Aam trTableBm1Aam : aamStorageReference) {
                        aamFrontendStorageReferenceMap.put(trTableBm1Aam.getTrTableAamPK(), trTableBm1Aam);
                    }

                    Set<TrTableBm1Aam> aamFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTableAamPK trTableAamPK : aamPKFromDatabaseSet) {
                        if (!aamPKFrontendStorageReferenceSet.contains(trTableAamPK)) {
                            aamFrontendStorageReferenceSet.add(aamFrontendStorageReferenceMap.get(trTableAamPK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(aamFrontendStorageReferenceSet)) {
                        aamStorageReference.removeAll(aamFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm1Aam");
                    }
                }
            }
        }

        return countModification;
    }

    private Integer calculateAdditionsAndRemovalsBm3Akb(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTableAkbPK>> akbbm3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTableAkbPK>> akbbm3PKFromFrontend,
            Map<TrTableBm3PK, Set<TrTableBm3Akb>> bm3StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm3PK bm3PKFromDatabase : akbbm3PKFromDatabase.keySet()) {
            if (akbbm3PKFromFrontend.containsKey(bm3PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm3PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTableAkbPK> akbPKFromFrontendSet = akbbm3PKFromFrontend.get(bm3PKFromDatabase);
                Set<TrTableAkbPK> akbPKFromDatabaseSet = akbbm3PKFromDatabase.get(bm3PKFromDatabase);
                Set<TrTableBm3Akb> akbStorageReference = bm3StorageSets.get(bm3PKFromDatabase);

                Set<TrTableAkbPK> akbPKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTableAkbPK> pendingbm3Addition = new HashSet<>(akbPKFromFrontendSet);
                pendingbm3Addition.removeAll(akbPKFromDatabaseSet);
                Set<TrTableAkbPK> pendingbm1Removal = new HashSet<>(akbPKFromDatabaseSet);
                pendingbm1Removal.removeAll(akbPKFromFrontendSet);

                maxAbrechNr = calculateMaximumAkbBm3PK(akbPKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm3.getTrTableBm3PK().getKeySatzart().equals("H")
                            || !frontendBm3.getTrTableBm3PK().getKeySatzart()
                                    .equals(bm3PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm3Akb frontendBm3Akb : frontendBm3.getTrTableBm3AkbSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm3Akb.getTrTableAkbPK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm3Akb.getTrTableAkbPK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            akbStorageReference.add(frontendBm3Akb);
                        }
                        akbPKFrontendStorageReferenceSet.add(frontendBm3Akb.getTrTableAkbPK());
                    }
                }

                if (akbPKFromDatabaseSet != null
                        && akbPKFrontendStorageReferenceSet.size() != akbPKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }

                if (countModification > 0) {
                    Map<TrTableAkbPK, TrTableBm3Akb> akbFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm3Akb trTableBm3Akb : akbStorageReference) {
                        akbFrontendStorageReferenceMap.put(trTableBm3Akb.getTrTableAkbPK(), trTableBm3Akb);
                    }

                    Set<TrTableBm3Akb> akbFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTableAkbPK trTableAkbPK : akbPKFromDatabaseSet) {
                        if (!akbPKFrontendStorageReferenceSet.contains(trTableAkbPK)) {
                            akbFrontendStorageReferenceSet.add(akbFrontendStorageReferenceMap.get(trTableAkbPK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(akbFrontendStorageReferenceSet)) {
                        akbStorageReference.removeAll(akbFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm3Akb");
                    }
                }
            }
        }
        return countModification;
    }

    private Integer calculateAdditionsAndRemovalsBm1Ake(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTableAkePK>> akebm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTableAkePK>> akebm1PKFromFrontend,
            Map<TrTableBm1PK, Set<TrTableBm1Ake>> bm1StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm1PK bm1PKFromDatabase : akebm1PKFromDatabase.keySet()) {
            if (akebm1PKFromFrontend.containsKey(bm1PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm1PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTableAkePK> akePKFromFrontendSet = akebm1PKFromFrontend.get(bm1PKFromDatabase);
                Set<TrTableAkePK> akePKFromDatabaseSet = akebm1PKFromDatabase.get(bm1PKFromDatabase);
                Set<TrTableBm1Ake> akeStorageReference = bm1StorageSets.get(bm1PKFromDatabase);

                Set<TrTableAkePK> akePKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTableAkePK> pendingbm1Addition = new HashSet<>(akePKFromFrontendSet);
                pendingbm1Addition.removeAll(akePKFromDatabaseSet);
                Set<TrTableAkePK> pendingbm1Removal = new HashSet<>(akePKFromDatabaseSet);
                pendingbm1Removal.removeAll(akePKFromFrontendSet);

                maxAbrechNr = calculateMaximumAkePK(akePKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm1.getTrTableBm1PK().getKeySatzart().equals("H")
                            || !frontendBm1.getTrTableBm1PK().getKeySatzart()
                                    .equals(bm1PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm1Ake frontendBm1Ake : frontendBm1.getTrTableBm1AkeSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm1Ake.getTrTableAkePK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm1Ake.getTrTableAkePK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            akeStorageReference.add(frontendBm1Ake);
                        }
                        akePKFrontendStorageReferenceSet.add(frontendBm1Ake.getTrTableAkePK());
                    }
                }

                if (akePKFromDatabaseSet != null
                        && akePKFrontendStorageReferenceSet.size() != akePKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }

                if (countModification > 0) {
                    Map<TrTableAkePK, TrTableBm1Ake> akeFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm1Ake trTableBm1Ake : akeStorageReference) {
                        akeFrontendStorageReferenceMap.put(trTableBm1Ake.getTrTableAkePK(), trTableBm1Ake);
                    }

                    Set<TrTableBm1Ake> akeFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTableAkePK trTableAkePK : akePKFromDatabaseSet) {
                        if (!akePKFrontendStorageReferenceSet.contains(trTableAkePK)) {
                            akeFrontendStorageReferenceSet.add(akeFrontendStorageReferenceMap.get(trTableAkePK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(akeFrontendStorageReferenceSet)) {
                        akeStorageReference.removeAll(akeFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm1Ake");
                    }
                }
            }
        }
        return countModification;
    }

    private Integer calculateAdditionsAndRemovalsBm3Ake(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTableAkePK>> akebm3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTableAkePK>> akebm3PKFromFrontend,
            Map<TrTableBm3PK, Set<TrTableBm3Ake>> bm3StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm3PK bm3PKFromDatabase : akebm3PKFromDatabase.keySet()) {
            if (akebm3PKFromFrontend.containsKey(bm3PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm3PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTableAkePK> akePKFromFrontendSet = akebm3PKFromFrontend.get(bm3PKFromDatabase);
                Set<TrTableAkePK> akePKFromDatabaseSet = akebm3PKFromDatabase.get(bm3PKFromDatabase);
                Set<TrTableBm3Ake> akeStorageReference = bm3StorageSets.get(bm3PKFromDatabase);

                Set<TrTableAkePK> akePKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTableAkePK> pendingbm3Addition = new HashSet<>(akePKFromFrontendSet);
                pendingbm3Addition.removeAll(akePKFromDatabaseSet);
                Set<TrTableAkePK> pendingbm3Removal = new HashSet<>(akePKFromDatabaseSet);
                pendingbm3Removal.removeAll(akePKFromFrontendSet);

                maxAbrechNr = calculateMaximumAkeBm3PK(akePKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm3.getTrTableBm3PK().getKeySatzart().equals("H")
                            || !frontendBm3.getTrTableBm3PK().getKeySatzart()
                                    .equals(bm3PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm3Ake frontendBm3Ake : frontendBm3.getTrTableBm3AkeSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm3Ake.getTrTableAkePK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm3Ake.getTrTableAkePK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            akeStorageReference.add(frontendBm3Ake);
                        }
                        akePKFrontendStorageReferenceSet.add(frontendBm3Ake.getTrTableAkePK());
                    }
                }

                if (akePKFromDatabaseSet != null
                        && akePKFrontendStorageReferenceSet.size() != akePKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }

                if (countModification > 0) {
                    Map<TrTableAkePK, TrTableBm3Ake> akeFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm3Ake trTableBm3Ake : akeStorageReference) {
                        akeFrontendStorageReferenceMap.put(trTableBm3Ake.getTrTableAkePK(), trTableBm3Ake);
                    }

                    Set<TrTableBm3Ake> akeFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTableAkePK trTableAkePK : akePKFromDatabaseSet) {
                        if (!akePKFrontendStorageReferenceSet.contains(trTableAkePK)) {
                            akeFrontendStorageReferenceSet.add(akeFrontendStorageReferenceMap.get(trTableAkePK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(akeFrontendStorageReferenceSet)) {
                        akeStorageReference.removeAll(akeFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm3Ake");
                    }
                }
            }
        }
        return countModification;
    }

    private Integer calculateAdditionsAndRemovalsBm1Eik(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTableEikPK>> eikbm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTableEikPK>> eikbm1PKFromFrontend,
            Map<TrTableBm1PK, Set<TrTableBm1Eik>> bm1StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm1PK bm1PKFromDatabase : eikbm1PKFromDatabase.keySet()) {
            if (eikbm1PKFromFrontend.containsKey(bm1PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm1PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTableEikPK> eikPKFromFrontendSet = eikbm1PKFromFrontend.get(bm1PKFromDatabase);
                Set<TrTableEikPK> eikPKFromDatabaseSet = eikbm1PKFromDatabase.get(bm1PKFromDatabase);
                Set<TrTableBm1Eik> eikStorageReference = bm1StorageSets.get(bm1PKFromDatabase);

                Set<TrTableEikPK> eikPKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTableEikPK> pendingbm1Addition = new HashSet<>(eikPKFromFrontendSet);
                pendingbm1Addition.removeAll(eikPKFromDatabaseSet);
                Set<TrTableEikPK> pendingbm1Removal = new HashSet<>(eikPKFromDatabaseSet);
                pendingbm1Removal.removeAll(eikPKFromFrontendSet);

                maxAbrechNr = calculateMaximumEikPK(eikPKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm1.getTrTableBm1PK().getKeySatzart().equals("H")
                            || !frontendBm1.getTrTableBm1PK().getKeySatzart()
                                    .equals(bm1PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm1Eik frontendBm1Eik : frontendBm1.getTrTableBm1EikSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm1Eik.getTrTableEikPK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm1Eik.getTrTableEikPK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            eikStorageReference.add(frontendBm1Eik);
                        }

                        eikPKFrontendStorageReferenceSet.add(frontendBm1Eik.getTrTableEikPK());
                    }
                }

                if (eikPKFromDatabaseSet != null
                        && eikPKFrontendStorageReferenceSet.size() != eikPKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }

                if (countModification > 0) {
                    Map<TrTableEikPK, TrTableBm1Eik> eikFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm1Eik trTableBm1Eik : eikStorageReference) {
                        eikFrontendStorageReferenceMap.put(trTableBm1Eik.getTrTableEikPK(), trTableBm1Eik);
                    }

                    Set<TrTableBm1Eik> eikFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTableEikPK trTableEikPK : eikPKFromDatabaseSet) {
                        if (!eikPKFrontendStorageReferenceSet.contains(trTableEikPK)) {
                            eikFrontendStorageReferenceSet.add(eikFrontendStorageReferenceMap.get(trTableEikPK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(eikFrontendStorageReferenceSet)) {
                        eikStorageReference.removeAll(eikFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm1Eik");
                    }
                }
            }
        }

        return countModification;
    }

    private Integer calculateAdditionsAndRemovalsBm3Eik(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTableEikPK>> eikbm3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTableEikPK>> eikbm3PKFromFrontend,
            Map<TrTableBm3PK, Set<TrTableBm3Eik>> bm3StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm3PK bm3PKFromDatabase : eikbm3PKFromDatabase.keySet()) {
            if (eikbm3PKFromFrontend.containsKey(bm3PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm3PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTableEikPK> eikPKFromFrontendSet = eikbm3PKFromFrontend.get(bm3PKFromDatabase);
                Set<TrTableEikPK> eikPKFromDatabaseSet = eikbm3PKFromDatabase.get(bm3PKFromDatabase);
                Set<TrTableBm3Eik> eikStorageReference = bm3StorageSets.get(bm3PKFromDatabase);

                Set<TrTableEikPK> eikPKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTableEikPK> pendingbm3Addition = new HashSet<>(eikPKFromFrontendSet);
                pendingbm3Addition.removeAll(eikPKFromDatabaseSet);
                Set<TrTableEikPK> pendingbm3Removal = new HashSet<>(eikPKFromDatabaseSet);
                pendingbm3Removal.removeAll(eikPKFromFrontendSet);

                maxAbrechNr = calculateMaximumEikBm3PK(eikPKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm3.getTrTableBm3PK().getKeySatzart().equals("H")
                            || !frontendBm3.getTrTableBm3PK().getKeySatzart()
                                    .equals(bm3PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm3Eik frontendBm3Eik : frontendBm3.getTrTableBm3EikSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm3Eik.getTrTableEikPK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm3Eik.getTrTableEikPK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            eikStorageReference.add(frontendBm3Eik);
                        }
                        eikPKFrontendStorageReferenceSet.add(frontendBm3Eik.getTrTableEikPK());
                    }
                }
                if (eikPKFromDatabaseSet != null
                        && eikPKFrontendStorageReferenceSet.size() != eikPKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }
                if (countModification > 0) {
                    Map<TrTableEikPK, TrTableBm3Eik> eikFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm3Eik trTableBm3Eik : eikStorageReference) {
                        eikFrontendStorageReferenceMap.put(trTableBm3Eik.getTrTableEikPK(), trTableBm3Eik);
                    }

                    Set<TrTableBm3Eik> eikFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTableEikPK trTableEikPK : eikPKFromDatabaseSet) {
                        if (!eikPKFrontendStorageReferenceSet.contains(trTableEikPK)) {
                            eikFrontendStorageReferenceSet.add(eikFrontendStorageReferenceMap.get(trTableEikPK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(eikFrontendStorageReferenceSet)) {
                        eikStorageReference.removeAll(eikFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm3Eik");
                    }
                }
            }
        }

        return countModification;
    }

    private Integer calculateAdditionsAndRemovalsBm1Piv(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTablePivPK>> pivbm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTablePivPK>> pivbm1PKFromFrontend,
            Map<TrTableBm1PK, Set<TrTableBm1Piv>> bm1StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm1PK bm1PKFromDatabase : pivbm1PKFromDatabase.keySet()) {
            if (pivbm1PKFromFrontend.containsKey(bm1PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm1PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTablePivPK> pivPKFromFrontendSet = pivbm1PKFromFrontend.get(bm1PKFromDatabase);
                Set<TrTablePivPK> pivPKFromDatabaseSet = pivbm1PKFromDatabase.get(bm1PKFromDatabase);
                Set<TrTableBm1Piv> pivStorageReference = bm1StorageSets.get(bm1PKFromDatabase);

                Set<TrTablePivPK> pivPKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTablePivPK> pendingbm1Addition = new HashSet<>(pivPKFromFrontendSet);
                pendingbm1Addition.removeAll(pivPKFromDatabaseSet);
                Set<TrTablePivPK> pendingbm1Removal = new HashSet<>(pivPKFromDatabaseSet);
                pendingbm1Removal.removeAll(pivPKFromFrontendSet);

                maxAbrechNr = calculateMaximumPivPK(pivPKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm1.getTrTableBm1PK().getKeySatzart().equals("H")
                            || !frontendBm1.getTrTableBm1PK().getKeySatzart()
                                    .equals(bm1PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm1Piv frontendBm1Piv : frontendBm1.getTrTableBm1PivSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm1Piv.getTrTablePivPK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm1Piv.getTrTablePivPK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            pivStorageReference.add(frontendBm1Piv);
                        }
                        pivPKFrontendStorageReferenceSet.add(frontendBm1Piv.getTrTablePivPK());
                    }
                }

                if (pivPKFromDatabaseSet != null
                        && pivPKFrontendStorageReferenceSet.size() != pivPKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }
                if (countModification > 0) {
                    Map<TrTablePivPK, TrTableBm1Piv> pivFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm1Piv trTableBm1Piv : pivStorageReference) {
                        pivFrontendStorageReferenceMap.put(trTableBm1Piv.getTrTablePivPK(), trTableBm1Piv);
                    }

                    Set<TrTableBm1Piv> pivFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTablePivPK trTablePivPK : pivPKFromDatabaseSet) {
                        if (!pivPKFrontendStorageReferenceSet.contains(trTablePivPK)) {
                            pivFrontendStorageReferenceSet.add(pivFrontendStorageReferenceMap.get(trTablePivPK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(pivFrontendStorageReferenceSet)) {
                        pivStorageReference.removeAll(pivFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm1Piv");
                    }
                }
            }
        }
        return countModification;
    }

    private Integer calculateAdditionsAndRemovalsBm3Piv(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTablePivPK>> pivbm3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTablePivPK>> pivbm3PKFromFrontend,
            Map<TrTableBm3PK, Set<TrTableBm3Piv>> bm3StorageSets, String action) {
        int maxAbrechNr;
        int nextKeyAbrechNr;
        int countModification = 0;
        for (TrTableBm3PK bm3PKFromDatabase : pivbm3PKFromDatabase.keySet()) {
            if (pivbm3PKFromFrontend.containsKey(bm3PKFromDatabase)) {
                if (CommonConstants.UPDATE_ACTION.equals(action) && bm3PKFromDatabase.getKeySatzart().equals("H")) {
                    continue;
                }
                Set<TrTablePivPK> pivPKFromFrontendSet = pivbm3PKFromFrontend.get(bm3PKFromDatabase);
                Set<TrTablePivPK> pivPKFromDatabaseSet = pivbm3PKFromDatabase.get(bm3PKFromDatabase);
                Set<TrTableBm3Piv> pivStorageReference = bm3StorageSets.get(bm3PKFromDatabase);

                Set<TrTablePivPK> pivPKFrontendStorageReferenceSet = new HashSet<>();

                Set<TrTablePivPK> pendingbm3Addition = new HashSet<>(pivPKFromFrontendSet);
                pendingbm3Addition.removeAll(pivPKFromDatabaseSet);
                Set<TrTablePivPK> pendingbm1Removal = new HashSet<>(pivPKFromDatabaseSet);
                pendingbm1Removal.removeAll(pivPKFromFrontendSet);

                maxAbrechNr = calculateMaximumPivBm3PK(pivPKFromDatabaseSet);
                nextKeyAbrechNr = maxAbrechNr + 1;

                for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
                    if (CommonConstants.UPDATE_ACTION.equals(action)
                            && frontendBm3.getTrTableBm3PK().getKeySatzart().equals("H")
                            || !frontendBm3.getTrTableBm3PK().getKeySatzart()
                                    .equals(bm3PKFromDatabase.getKeySatzart())) {
                        continue;
                    }
                    for (TrTableBm3Piv frontendBm3Piv : frontendBm3.getTrTableBm3PivSet()) {
                        String frontendAbrechNr = Optional.ofNullable(frontendBm3Piv.getTrTablePivPK().getKeyAbrechNr())
                                .orElse("").trim();
                        if (frontendAbrechNr.isEmpty()) {
                            frontendBm3Piv.getTrTablePivPK().setKeyAbrechNr(String.format("%05d", nextKeyAbrechNr));
                            nextKeyAbrechNr++;
                            pivStorageReference.add(frontendBm3Piv);
                        }
                        pivPKFrontendStorageReferenceSet.add(frontendBm3Piv.getTrTablePivPK());
                    }
                }
                if (pivPKFromDatabaseSet != null
                        && pivPKFrontendStorageReferenceSet.size() != pivPKFromDatabaseSet.size()) {
                    countModification = countModification + 1;
                }
                if (countModification > 0) {
                    Map<TrTablePivPK, TrTableBm3Piv> pivFrontendStorageReferenceMap = new HashMap<>();
                    for (TrTableBm3Piv trTableBm3Piv : pivStorageReference) {
                        pivFrontendStorageReferenceMap.put(trTableBm3Piv.getTrTablePivPK(), trTableBm3Piv);
                    }

                    Set<TrTableBm3Piv> pivFrontendStorageReferenceSet = new HashSet<>();
                    for (TrTablePivPK trTablePivPK : pivPKFromDatabaseSet) {
                        if (!pivPKFrontendStorageReferenceSet.contains(trTablePivPK)) {
                            pivFrontendStorageReferenceSet.add(pivFrontendStorageReferenceMap.get(trTablePivPK));
                        }
                    }
                    if (!ObjectUtils.isEmpty(pivFrontendStorageReferenceSet)) {
                        pivStorageReference.removeAll(pivFrontendStorageReferenceSet);
                        LOG.trace("Removed subtables in TrTableBm3Piv");
                    }
                }
            }
        }
        return countModification;
    }

    private void cacheBm1AamDatabase(TrTableBsb databaseBsb, Map<TrTableBm1PK, Set<TrTableAamPK>> aambm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTableBm1Aam>> bm1StorageSets) {
        for (TrTableBm1 databaseBm1 : databaseBsb.getTrTableBm1Set()) {
            aambm1PKFromDatabase.put(databaseBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            bm1StorageSets.put(databaseBm1.getTrTableBm1PK(), databaseBm1.getTrTableBm1AamSet());
            for (TrTableBm1Aam databaseAam : databaseBm1.getTrTableBm1AamSet()) {
                TrTableAamPK databaseAamPK = databaseAam.getTrTableAamPK();
                LOG.trace("collect backend {} for bm1 {}", databaseAamPK.getKeyAbrechNr(),
                        databaseBm1.getTrTableBm1PK().getKeySatzart());
                aambm1PKFromDatabase.get(databaseBm1.getTrTableBm1PK()).add(databaseAamPK);
            }
        }
    }

    private void cacheBm3AkbDatabase(TrTableBsb databaseBsb, Map<TrTableBm3PK, Set<TrTableAkbPK>> akbbm3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTableBm3Akb>> bm3StorageSets) {
        for (TrTableBm3 databaseBm3 : databaseBsb.getTrTableBm3Set()) {
            akbbm3PKFromDatabase.put(databaseBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            bm3StorageSets.put(databaseBm3.getTrTableBm3PK(), databaseBm3.getTrTableBm3AkbSet());
            for (TrTableBm3Akb databaseAkb : databaseBm3.getTrTableBm3AkbSet()) {
                TrTableAkbPK databaseAamPK = databaseAkb.getTrTableAkbPK();
                LOG.trace("collect backend {} for bm3 {}", databaseAamPK.getKeyAbrechNr(),
                        databaseBm3.getTrTableBm3PK().getKeySatzart());
                akbbm3PKFromDatabase.get(databaseBm3.getTrTableBm3PK()).add(databaseAamPK);
            }
        }
    }

    private void cacheBm1AkeDatabase(TrTableBsb databaseBsb, Map<TrTableBm1PK, Set<TrTableAkePK>> akebm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTableBm1Ake>> bm1StorageSets) {
        for (TrTableBm1 databaseBm1 : databaseBsb.getTrTableBm1Set()) {
            akebm1PKFromDatabase.put(databaseBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            bm1StorageSets.put(databaseBm1.getTrTableBm1PK(), databaseBm1.getTrTableBm1AkeSet());
            for (TrTableBm1Ake databaseAke : databaseBm1.getTrTableBm1AkeSet()) {
                TrTableAkePK databaseAkePK = databaseAke.getTrTableAkePK();
                LOG.trace("collect backend {} for bm1 {}", databaseAkePK.getKeyAbrechNr(),
                        databaseBm1.getTrTableBm1PK().getKeySatzart());
                akebm1PKFromDatabase.get(databaseBm1.getTrTableBm1PK()).add(databaseAkePK);
            }
        }
    }

    private void cacheBm3AkeDatabase(TrTableBsb databaseBsb, Map<TrTableBm3PK, Set<TrTableAkePK>> akem3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTableBm3Ake>> bm1StorageSets) {
        for (TrTableBm3 databaseBm3 : databaseBsb.getTrTableBm3Set()) {
            akem3PKFromDatabase.put(databaseBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            bm1StorageSets.put(databaseBm3.getTrTableBm3PK(), databaseBm3.getTrTableBm3AkeSet());
            for (TrTableBm3Ake databaseAke : databaseBm3.getTrTableBm3AkeSet()) {
                TrTableAkePK databaseAkePK = databaseAke.getTrTableAkePK();
                LOG.trace("collect backend {} for bm3 {}", databaseAkePK.getKeyAbrechNr(),
                        databaseBm3.getTrTableBm3PK().getKeySatzart());
                akem3PKFromDatabase.get(databaseBm3.getTrTableBm3PK()).add(databaseAkePK);
            }
        }
    }

    private void cacheBm1EikDatabase(TrTableBsb databaseBsb, Map<TrTableBm1PK, Set<TrTableEikPK>> eikbm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTableBm1Eik>> bm1StorageSets) {
        for (TrTableBm1 databaseBm1 : databaseBsb.getTrTableBm1Set()) {
            eikbm1PKFromDatabase.put(databaseBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            bm1StorageSets.put(databaseBm1.getTrTableBm1PK(), databaseBm1.getTrTableBm1EikSet());
            for (TrTableBm1Eik databaseEik : databaseBm1.getTrTableBm1EikSet()) {
                TrTableEikPK databaseEikPK = databaseEik.getTrTableEikPK();
                LOG.trace("collect backend {} for bm1 {}", databaseEikPK.getKeyAbrechNr(),
                        databaseBm1.getTrTableBm1PK().getKeySatzart());
                eikbm1PKFromDatabase.get(databaseBm1.getTrTableBm1PK()).add(databaseEikPK);
            }
        }

    }

    private void cacheBm3EikDatabase(TrTableBsb databaseBsb, Map<TrTableBm3PK, Set<TrTableEikPK>> eikbm3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTableBm3Eik>> bm3StorageSets) {
        for (TrTableBm3 databaseBm3 : databaseBsb.getTrTableBm3Set()) {
            eikbm3PKFromDatabase.put(databaseBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            bm3StorageSets.put(databaseBm3.getTrTableBm3PK(), databaseBm3.getTrTableBm3EikSet());
            for (TrTableBm3Eik databaseEik : databaseBm3.getTrTableBm3EikSet()) {
                TrTableEikPK databaseEikPK = databaseEik.getTrTableEikPK();
                LOG.trace("collect backend {} for bm3 {}", databaseEikPK.getKeyAbrechNr(),
                        databaseBm3.getTrTableBm3PK().getKeySatzart());
                eikbm3PKFromDatabase.get(databaseBm3.getTrTableBm3PK()).add(databaseEikPK);
            }
        }

    }

    private void cacheBm1PivDatabase(TrTableBsb databaseBsb, Map<TrTableBm1PK, Set<TrTablePivPK>> pivbm1PKFromDatabase,
            Map<TrTableBm1PK, Set<TrTableBm1Piv>> bm1StorageSets) {
        for (TrTableBm1 databaseBm1 : databaseBsb.getTrTableBm1Set()) {
            pivbm1PKFromDatabase.put(databaseBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            bm1StorageSets.put(databaseBm1.getTrTableBm1PK(), databaseBm1.getTrTableBm1PivSet());
            for (TrTableBm1Piv databasePiv : databaseBm1.getTrTableBm1PivSet()) {
                TrTablePivPK databasePivPK = databasePiv.getTrTablePivPK();
                LOG.trace("collect backend {} for bm1 {}", databasePivPK.getKeyAbrechNr(),
                        databaseBm1.getTrTableBm1PK().getKeySatzart());
                pivbm1PKFromDatabase.get(databaseBm1.getTrTableBm1PK()).add(databasePivPK);
            }
        }
    }

    private void cacheBm3PivDatabase(TrTableBsb databaseBsb, Map<TrTableBm3PK, Set<TrTablePivPK>> pivbm3PKFromDatabase,
            Map<TrTableBm3PK, Set<TrTableBm3Piv>> bm3StorageSets) {
        for (TrTableBm3 databaseBm3 : databaseBsb.getTrTableBm3Set()) {
            pivbm3PKFromDatabase.put(databaseBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            bm3StorageSets.put(databaseBm3.getTrTableBm3PK(), databaseBm3.getTrTableBm3PivSet());
            for (TrTableBm3Piv databasePiv : databaseBm3.getTrTableBm3PivSet()) {
                TrTablePivPK databasePivPK = databasePiv.getTrTablePivPK();
                LOG.trace("collect backend {} for bm3 {}", databasePivPK.getKeyAbrechNr(),
                        databaseBm3.getTrTableBm3PK().getKeySatzart());
                pivbm3PKFromDatabase.get(databaseBm3.getTrTableBm3PK()).add(databasePivPK);
            }
        }
    }

    private void cacheBm1AamPKFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTableAamPK>> aambm1PKFromFrontend) {
        for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
            aambm1PKFromFrontend.put(frontendBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            for (TrTableBm1Aam frontendAam : frontendBm1.getTrTableBm1AamSet()) {
                TrTableAamPK frontendAamPK = frontendAam.getTrTableAamPK();
                LOG.trace("collect frontend {} for bm1 {}", frontendAamPK.getKeyAbrechNr(),
                        frontendBm1.getTrTableBm1PK().getKeySatzart());
                aambm1PKFromFrontend.get(frontendBm1.getTrTableBm1PK()).add(frontendAamPK);
            }
        }
    }

    private void cacheBm3AkbPKFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTableAkbPK>> akbbm3PKFromFrontend) {
        for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
            akbbm3PKFromFrontend.put(frontendBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            for (TrTableBm3Akb frontendAkb : frontendBm3.getTrTableBm3AkbSet()) {
                TrTableAkbPK frontendAkbPK = frontendAkb.getTrTableAkbPK();
                LOG.trace("collect frontend {} for bm3 {}", frontendAkbPK.getKeyAbrechNr(),
                        frontendBm3.getTrTableBm3PK().getKeySatzart());
                akbbm3PKFromFrontend.get(frontendBm3.getTrTableBm3PK()).add(frontendAkbPK);
            }
        }
    }

    private void cacheBm1AkeFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTableAkePK>> akebm1PKFromFrontend) {
        for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
            akebm1PKFromFrontend.put(frontendBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            for (TrTableBm1Ake frontendAke : frontendBm1.getTrTableBm1AkeSet()) {
                TrTableAkePK frontendAkePK = frontendAke.getTrTableAkePK();
                LOG.trace("collect frontend {} for bm1 {}", frontendAkePK.getKeyAbrechNr(),
                        frontendBm1.getTrTableBm1PK().getKeySatzart());
                akebm1PKFromFrontend.get(frontendBm1.getTrTableBm1PK()).add(frontendAkePK);
            }
        }
    }

    private void cacheBm3AkeFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTableAkePK>> akebm3PKFromFrontend) {
        for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
            akebm3PKFromFrontend.put(frontendBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            for (TrTableBm3Ake frontendAke : frontendBm3.getTrTableBm3AkeSet()) {
                TrTableAkePK frontendAkePK = frontendAke.getTrTableAkePK();
                LOG.trace("collect frontend {} for bm3 {}", frontendAkePK.getKeyAbrechNr(),
                        frontendBm3.getTrTableBm3PK().getKeySatzart());
                akebm3PKFromFrontend.get(frontendBm3.getTrTableBm3PK()).add(frontendAkePK);
            }
        }
    }

    private void cacheBm1EikFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTableEikPK>> eikbm1PKFromFrontend) {
        for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
            eikbm1PKFromFrontend.put(frontendBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            for (TrTableBm1Eik frontendEik : frontendBm1.getTrTableBm1EikSet()) {
                TrTableEikPK frontendEikPK = frontendEik.getTrTableEikPK();
                LOG.trace("collect frontend {} for bm1 {}", frontendEikPK.getKeyAbrechNr(),
                        frontendBm1.getTrTableBm1PK().getKeySatzart());
                eikbm1PKFromFrontend.get(frontendBm1.getTrTableBm1PK()).add(frontendEikPK);
            }
        }
    }

    private void cacheBm3EikFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTableEikPK>> eikbm3PKFromFrontend) {
        for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
            eikbm3PKFromFrontend.put(frontendBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            for (TrTableBm3Eik frontendEik : frontendBm3.getTrTableBm3EikSet()) {
                TrTableEikPK frontendEikPK = frontendEik.getTrTableEikPK();
                LOG.trace("collect frontend {} for bm3 {}", frontendEikPK.getKeyAbrechNr(),
                        frontendBm3.getTrTableBm3PK().getKeySatzart());
                eikbm3PKFromFrontend.get(frontendBm3.getTrTableBm3PK()).add(frontendEikPK);
            }
        }
    }

    private void cacheBm1PivFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm1PK, Set<TrTablePivPK>> pivbm1PKFromFrontend) {
        for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
            pivbm1PKFromFrontend.put(frontendBm1.getTrTableBm1PK(), new LinkedHashSet<>());
            for (TrTableBm1Piv frontendPiv : frontendBm1.getTrTableBm1PivSet()) {
                TrTablePivPK frontendPivPK = frontendPiv.getTrTablePivPK();
                LOG.trace("collect frontend {} for bm1 {}", frontendPivPK.getKeyAbrechNr(),
                        frontendBm1.getTrTableBm1PK().getKeySatzart());
                pivbm1PKFromFrontend.get(frontendBm1.getTrTableBm1PK()).add(frontendPivPK);
            }
        }

    }

    private void cacheBm3PivFrontend(TrTableBsb frontendBsb,
            Map<TrTableBm3PK, Set<TrTablePivPK>> pivbm3PKFromFrontend) {
        for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
            pivbm3PKFromFrontend.put(frontendBm3.getTrTableBm3PK(), new LinkedHashSet<>());
            for (TrTableBm3Piv frontendPiv : frontendBm3.getTrTableBm3PivSet()) {
                TrTablePivPK frontendPivPK = frontendPiv.getTrTablePivPK();
                LOG.trace("collect frontend {} for bm3 {}", frontendPivPK.getKeyAbrechNr(),
                        frontendBm3.getTrTableBm3PK().getKeySatzart());
                pivbm3PKFromFrontend.get(frontendBm3.getTrTableBm3PK()).add(frontendPivPK);
            }
        }

    }

    private int calculateMaximumAamPK(Set<TrTableAamPK> Bm1AamPKSet) {
        int maxAbrechNr;
        int AbrechNr;
        maxAbrechNr = -1;

        for (TrTableAamPK bm1aamPk : Bm1AamPKSet) {
            AbrechNr = Integer.parseInt(bm1aamPk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }
        return maxAbrechNr;
    }

    private int calculateMaximumAkbBm3PK(Set<TrTableAkbPK> Bm3AkbPKSet) {
        int maxAbrechNr, AbrechNr;
        maxAbrechNr = -1;

        for (TrTableAkbPK bm3akbPk : Bm3AkbPKSet) {
            AbrechNr = Integer.parseInt(bm3akbPk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }
        return maxAbrechNr;
    }

    private int calculateMaximumAkePK(Set<TrTableAkePK> Bm1AkePKSet) {
        int maxAbrechNr, AbrechNr;
        maxAbrechNr = -1;
        for (TrTableAkePK bm1akePk : Bm1AkePKSet) {
            AbrechNr = Integer.parseInt(bm1akePk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }
        return maxAbrechNr;
    }

    private int calculateMaximumAkeBm3PK(Set<TrTableAkePK> Bm3AkePKSet) {
        int maxAbrechNr, AbrechNr;
        maxAbrechNr = -1;
        for (TrTableAkePK bm3akePk : Bm3AkePKSet) {
            AbrechNr = Integer.parseInt(bm3akePk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }
        return maxAbrechNr;
    }

    private int calculateMaximumEikPK(Set<TrTableEikPK> Bm1EikPKSet) {
        int maxAbrechNr, AbrechNr;
        maxAbrechNr = -1;
        for (TrTableEikPK bm1eikPk : Bm1EikPKSet) {
            AbrechNr = Integer.parseInt(bm1eikPk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }
        return maxAbrechNr;
    }

    private int calculateMaximumEikBm3PK(Set<TrTableEikPK> Bm3EikPKSet) {
        int maxAbrechNr, AbrechNr;
        maxAbrechNr = -1;
        for (TrTableEikPK bm1eikPk : Bm3EikPKSet) {
            AbrechNr = Integer.parseInt(bm1eikPk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }

        return maxAbrechNr;
    }

    private int calculateMaximumPivPK(Set<TrTablePivPK> Bm1PivPKSet) {
        int maxAbrechNr, AbrechNr;
        maxAbrechNr = -1;
        for (TrTablePivPK bm1pivPk : Bm1PivPKSet) {
            AbrechNr = Integer.parseInt(bm1pivPk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }
        return maxAbrechNr;
    }

    private int calculateMaximumPivBm3PK(Set<TrTablePivPK> Bm3PivPKSet) {
        int maxAbrechNr, AbrechNr;
        maxAbrechNr = -1;
        for (TrTablePivPK bm3pivPk : Bm3PivPKSet) {
            AbrechNr = Integer.parseInt(bm3pivPk.getKeyAbrechNr());
            if (AbrechNr > maxAbrechNr) {
                maxAbrechNr = AbrechNr;
            }
        }
        return maxAbrechNr;
    }
}
