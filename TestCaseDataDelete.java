package functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "clerka", password = "password123", authorities = { "DELETE_TAXREPORTING" })
public class TestCaseDataDelete extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_B_before_BM1;
    private List<TrTableBsb> bsbList_B_after_BM1;
    private List<TrTableBsb> bsbList_K_before_BM1;
    private List<TrTableBsb> bsbList_K_after_BM1;
    private List<TrTableBsb> bsbList_S_before_BM1;
    private List<TrTableBsb> bsbList_S_after_BM1;
    private List<TrTableBsb> bsbList_B_before_BM3;
    private List<TrTableBsb> bsbList_B_after_BM3;
    private List<TrTableBsb> bsbList_K_before_BM3;
    private List<TrTableBsb> bsbList_K_after_BM3;
    private List<TrTableBsb> bsbList_S_before_BM3;
    private List<TrTableBsb> bsbList_S_after_BM3;

    @Autowired
    Journaling journaling;

    @Autowired
    MockMvc mvc;

    @BeforeAll
    void setupClass() {
        bsbList_B_before_BM1 = this.getTestCaseData("TEST001.before", "Testcases_UC07.xlsx");
        bsbList_B_after_BM1 = this.getTestCaseData("TEST001.after", "Testcases_UC07.xlsx");
        bsbList_K_before_BM1 = this.getTestCaseData("TEST002.before", "Testcases_UC07.xlsx");
        bsbList_K_after_BM1 = this.getTestCaseData("TEST002.after", "Testcases_UC07.xlsx");
        bsbList_S_before_BM1 = this.getTestCaseData("TEST003.before", "Testcases_UC07.xlsx");
        bsbList_S_after_BM1 = this.getTestCaseData("TEST003.after", "Testcases_UC07.xlsx");

        bsbList_B_before_BM3 = this.getTestCaseData("TEST004.before", "Testcases_UC07.xlsx");
        bsbList_B_after_BM3 = this.getTestCaseData("TEST004.after", "Testcases_UC07.xlsx");
        bsbList_K_before_BM3 = this.getTestCaseData("TEST005.before", "Testcases_UC07.xlsx");
        bsbList_K_after_BM3 = this.getTestCaseData("TEST005.after", "Testcases_UC07.xlsx");
        bsbList_S_before_BM3 = this.getTestCaseData("TEST006.before", "Testcases_UC07.xlsx");
        bsbList_S_after_BM3 = this.getTestCaseData("TEST006.after", "Testcases_UC07.xlsx");

        timeProvider.stopTime();
    }

    private TrTableBsb getByMandSlAndKeyIdNR(TrTableBsb bsb) {
        return findInDBByKey(bsb);
    }

    @ParameterizedTest
    @MethodSource("provideStatusB_MeldeStatusOk")
    void Test_BM1_Status_B_meldeStatus_OK(String meldeStatus) throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_B_before_BM1.get(0));
        baseBsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(baseBsb);
        this.storeChanges();

        baseBsb = getByMandSlAndKeyIdNR(baseBsb);

        TrTableBsb bsb_after = bsbList_B_after_BM1.get(0);

        ValidationResults validationResults = this.callDeleteAPI(baseBsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_108_DESC));

        TrTableBsb stored_bsb_after_request = getByMandSlAndKeyIdNR(baseBsb);

        assertEquals(bsb_after.getStatus(), stored_bsb_after_request.getStatus());

        journaling.assertJournaling(baseBsb, "UC07", "TEST_B_RECORD_MELDE_STATUS_" + meldeStatus);
    }

    @ParameterizedTest
    @MethodSource("provideStatusB_MeldeStatusFailed")
    void Test_BM1_Status_B_meldeStatus_Failed(String meldeStatus) throws Exception {
        var bsb = cloneBsbDeep(bsbList_B_before_BM1.get(0));
        bsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(bsb);

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_104_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);
        assertEquals(result, bsb);

    }

    @ParameterizedTest
    @MethodSource("provideStatusK_MeldeStatusOk")
    void Test_BM1_Status_K_meldeStatus_OK(String meldeStatus) throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_K_before_BM1.get(1));
        baseBsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(baseBsb);
        this.storeChanges();

        baseBsb = getByMandSlAndKeyIdNR(baseBsb);

        TrTableBsb bsb_after = bsbList_K_after_BM1.get(1);

        ValidationResults validationResults = this.callDeleteAPI(baseBsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_108_DESC));

        TrTableBsb stored_bsb_after_request = getByMandSlAndKeyIdNR(baseBsb);

        assertNotNull(stored_bsb_after_request.getBedbsbTimestamp());
        assertEquals(bsb_after.getTrTableBm1Set().size(), stored_bsb_after_request.getTrTableBm1Set().size());
        assertEquals(bsb_after.getStatus(), stored_bsb_after_request.getStatus());
        assertEquals(bsb_after.getStornoKz(), stored_bsb_after_request.getStornoKz());
        assertEquals(bsb_after.getKmId(), stored_bsb_after_request.getKmId());
        assertEquals(bsb_after.getAnlass(), stored_bsb_after_request.getAnlass());
        assertEquals(bsb_after.getMeldeStatus(), stored_bsb_after_request.getMeldeStatus());

        journaling.assertJournaling(baseBsb, "UC07", "TEST_K_RECORD_MELDE_STATUS_" + meldeStatus);
    }

    @ParameterizedTest
    @MethodSource("provideStatusK_MeldeStatusFailed")
    void Test_BM1_Status_K_meldeStatus_Failed(String meldeStatus) throws Exception {
        var bsb = cloneBsbDeep(bsbList_K_before_BM1.get(1));
        bsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(bsb);
        this.storeChanges();

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_104_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);

        result.getTrTableBsbPK().getKeyIdNr().trim();

        assertEquals(result, bsb);
    }

    @Test
    void Test_BM1_Status_S_OK() throws Exception {
        var bsb = cloneBsbDeep(bsbList_S_before_BM1.get(2));
        bsb.setMeldungJjjjmmtt(new BigDecimal(timeProvider.getCurrentTime("yyyyMMdd")));
        bsb.setMeldungUhrHms(new BigDecimal(timeProvider.getCurrentTime("HHmmss")));

        taxReportingRepository.save(bsb);

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_108_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);
        assertEquals(result.getStatus(), "H");
        assertEquals(result.getNdTicket(), null);
        assertEquals(result.getMeldeStatus(), "  ");
        assertEquals(result.getAntwortStatus(), null);
        assertEquals(result.getAenderErfasser(), " ");
        assertEquals(result.getAntwortFehlerNr(), null);
        assertNotNull(result.getBedbsbTimestamp());
        assertEquals(result.getStornoKz(), " ");

        assertEquals(result.getKmId(), " ");
        assertEquals(result.getRefKmId(), null);

        assertEquals(bsbList_S_after_BM1.size(), 3);

    }

    @Test
    void Test_BM1_Status_H_Failed() throws Exception {
        var bsb = cloneBsbDeep(bsbList_B_before_BM1.get(0));
        bsb.setStatus("H");
        taxReportingRepository.save(bsb);
        this.storeChanges();

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_104_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);
        assertEquals(result, bsb);
    }

    @ParameterizedTest
    @MethodSource("provideStatusB_MeldeStatusOk")
    void Test_BM3_Status_B_meldeStatus_OK(String meldeStatus) throws Exception {
        var bsb = cloneBsbDeep(bsbList_B_before_BM3.get(0));
        bsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(bsb);

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_108_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);

        assertEquals(bsbList_B_after_BM3.get(0).getStatus(), result.getStatus());

    }

    @ParameterizedTest
    @MethodSource("provideStatusB_MeldeStatusFailed")
    void Test_BM3_Status_B_meldeStatus_Failed(String meldeStatus) throws Exception {
        var bsb = cloneBsbDeep(bsbList_B_before_BM3.get(0));
        bsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(bsb);

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_104_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);
        assertEquals(result, bsb);

    }

    @ParameterizedTest
    @MethodSource("provideStatusK_MeldeStatusOk")
    void Test_BM3_Status_K_meldeStatus_OK(String meldeStatus) throws Exception {
        TrTableBsb bsb = cloneBsbDeep(bsbList_K_before_BM3.get(1));
        bsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(bsb);
        this.storeChanges();

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_108_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);

        assertEquals(1, result.getTrTableBm3Set().size());
        assertEquals("H", result.getStatus());
        assertEquals(" ", result.getStornoKz());
        assertEquals(" ", result.getKmId());
        assertEquals(" ", result.getAnlass());
        assertEquals("  ", result.getMeldeStatus());
        assertNotNull(result.getBedbsbTimestamp());
        result.setBedbsbTimestamp(bsb.getBedbsbTimestamp());
        assertEquals(result, cloneBsbDeep(bsbList_K_after_BM3.get(1)));
    }

    @ParameterizedTest
    @MethodSource("provideStatusK_MeldeStatusFailed")
    void Test_BM3_Status_K_meldeStatus_Failed(String meldeStatus) throws Exception {
        var bsb = cloneBsbDeep(bsbList_K_before_BM3.get(1));
        bsb.setMeldeStatus(meldeStatus);
        taxReportingRepository.save(bsb);
        this.storeChanges();

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_104_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);
        assertEquals(result, bsb);
    }

    @Test
    void Test_BM3_Status_S_OK() throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_S_before_BM3.get(2));
        TrTableBsb bsb_after = cloneBsbDeep(bsbList_S_after_BM3.get(2));

        baseBsb.setMeldungJjjjmmtt(new BigDecimal(timeProvider.getCurrentTime("yyyyMMdd")));
        baseBsb.setMeldungUhrHms(new BigDecimal(timeProvider.getCurrentTime("HHmmss")));

        taxReportingRepository.save(baseBsb);

        ValidationResults validationResults = this.callDeleteAPI(baseBsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_108_DESC));

        TrTableBsb stored_bsb_after_request = findStoredBsb(baseBsb.getTrTableBsbPK());

        assertEquals("H", stored_bsb_after_request.getStatus());
        assertEquals(null, stored_bsb_after_request.getNdTicket());
        assertEquals("  ", stored_bsb_after_request.getMeldeStatus());
        assertEquals(null, stored_bsb_after_request.getAntwortStatus());
        assertEquals(" ", stored_bsb_after_request.getAenderErfasser());
        assertEquals(null, stored_bsb_after_request.getAntwortFehlerNr());
        assertNotNull(stored_bsb_after_request.getBedbsbTimestamp());
        assertEquals(" ", stored_bsb_after_request.getStornoKz());
        assertEquals(" ", stored_bsb_after_request.getKmId());
        assertEquals(null, stored_bsb_after_request.getRefKmId());
        assertEquals(3, bsbList_S_after_BM3.size());

        journaling.assertJournaling(baseBsb, "UC07", "TEST_S_RECORD");

    }

    @Test
    void Test_BM3_Status_H_Failed() throws Exception {
        var bsb = cloneBsbDeep(bsbList_B_before_BM3.get(0));
        bsb.setStatus("H");
        taxReportingRepository.save(bsb);
        this.storeChanges();

        ValidationResults validationResults = this.callDeleteAPI(bsb, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_104_DESC));

        TrTableBsb result = getByMandSlAndKeyIdNR(bsb);
        assertEquals(result, bsb);
    }

    private ValidationResults callDeleteAPI(TrTableBsb bsb, MockMvc mvc) throws Exception {
        TrTableBsbPK bsbPK = bsb.getTrTableBsbPK();
        String mandSl = bsbPK.getMandSl();
        long keyMeldejahr = bsbPK.getKeyMeldejahr();
        String keyMuster = bsbPK.getKeyMuster();
        long keyLaufnummer = bsbPK.getKeyLaufnummer();
        long keySysDatum = bsbPK.getKeySysDatum();
        long keyUhrzeit = bsbPK.getKeyUhrzeit();
        String keyIdNr = bsbPK.getKeyIdNr();

        ValidationResults validationResults = this.deleteBsbRequest(mandSl, keyMeldejahr, keyMuster, keyLaufnummer,
                keySysDatum, keyUhrzeit, keyIdNr, mvc);

        return validationResults;
    }

    private Stream<String> provideStatusB_MeldeStatusOk() {
        String[] allOptions = { "00", "01", "02", "03", "04", "05", "06" };
        return Arrays.stream(allOptions);
    }

    private Stream<String> provideStatusB_MeldeStatusFailed() {
        String[] allOptions = { "07", "08", "09", "10", "11", "12", "13" };
        return Arrays.stream(allOptions);
    }

    private Stream<String> provideStatusK_MeldeStatusOk() {
        String[] allOptions = { "00", "01", "02", "03", "04", "05", "06" };
        return Arrays.stream(allOptions);
    }

    private Stream<String> provideStatusK_MeldeStatusFailed() {
        String[] allOptions = { "07", "08", "09", "10", "11", "12", "13" };
        return Arrays.stream(allOptions);
    }
}
