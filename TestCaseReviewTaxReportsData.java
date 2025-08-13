package functional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "clerkA", password = "password123", authorities = { "APPROVE_TAXREPORTING" })
public class TestCaseReviewTaxReportsData extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_test001_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_test001_after = new ArrayList<>();

    private List<TrTableBsb> bsbList_test002_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_test002_after = new ArrayList<>();

    private List<TrTableBsb> bsbList_test003_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_test003_after = new ArrayList<>();

    private List<TrTableBsb> bsbList_test004_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_test004_after = new ArrayList<>();

    private List<TrTableBsb> bsbList_test005_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_test005_after = new ArrayList<>();

    private List<TrTableBsb> bsbList_test006_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_test006_after = new ArrayList<>();

    @Autowired
    Journaling journaling;

    @BeforeAll
    void setupClass() {
        // Action - OK and BSB with STATUS = B
        bsbList_test001_before = this.getTestCaseData("TEST001.before", "Testcases_UC06.xlsx");
        bsbList_test001_after = this.getTestCaseData("TEST001.after", "Testcases_UC06.xlsx");

        // Action - OK and BSB with STATUS = K
        bsbList_test003_before = this.getTestCaseData("TEST003.before", "Testcases_UC06.xlsx");
        bsbList_test003_after = this.getTestCaseData("TEST003.after", "Testcases_UC06.xlsx");

        // Action - OK and BSB with STATUS = S
        bsbList_test005_before = this.getTestCaseData("TEST005.before", "Testcases_UC06.xlsx");
        bsbList_test005_after = this.getTestCaseData("TEST005.after", "Testcases_UC06.xlsx");

        // Action - NOK and BSB with STATUS = B
        bsbList_test006_before = this.getTestCaseData("TEST006.before", "Testcases_UC06.xlsx");
        bsbList_test006_after = this.getTestCaseData("TEST006.after", "Testcases_UC06.xlsx");

        // Action - NOK and BSB with STATUS = K
        bsbList_test002_before = this.getTestCaseData("TEST002.before", "Testcases_UC06.xlsx");
        bsbList_test002_after = this.getTestCaseData("TEST002.after", "Testcases_UC06.xlsx");

        // Action - NOK and BSB with STATUS = S
        bsbList_test004_before = this.getTestCaseData("TEST004.before", "Testcases_UC06.xlsx");
        bsbList_test004_after = this.getTestCaseData("TEST004.after", "Testcases_UC06.xlsx");

        timeProvider.stopTime();

    }

    // Action - OK and success scenario
    @ParameterizedTest
    @CsvSource({ "test001", "test003", "test005" })
    void test_action_ok_success(String testName, @Autowired MockMvc mvc) throws Exception {
        // bsb_before
        TrTableBsb bsb_before = getBsbBefore(testName);
        taxReportingRepository.save(bsb_before);

        // action
        String action = "OK";

        timeProvider.setDateTime("20250412", "203456");

        // call review API
        ValidationResults validationResults = callReviewAPI(bsb_before, action, mvc);
        String actualMeldeStatus = validationResults.getValidationResults().get(1).getMessage();

        // bsb_after
        TrTableBsb bsb_after = getBsbAfter(testName);
        String expectedMeldeStatus = bsb_after.getMeldeStatus();

        // Assertion
        assertEquals(expectedMeldeStatus, actualMeldeStatus);

        journaling.assertJournaling(getBsbBefore(testName), "UC06", "TEST_OK_RECORD_" + testName.toUpperCase());
    }

    // Action - OK and failure scenario
    @ParameterizedTest
    @CsvSource({ "test001", "test003", "test005" })
    void test_action_ok_failure(String testName, @Autowired MockMvc mvc) throws Exception {
        // bsb_before
        TrTableBsb bsb_before = getBsbBefore(testName);
        bsb_before.setStatus("X"); // Updated to STATUS-X
        taxReportingRepository.save(bsb_before);

        // action
        String action = "OK";

        // call review API
        ValidationResults validationResults = callReviewAPI(bsb_before, action, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        // Assertion
        assertTrue(actualMessage.contains(ValidationMessage.ERROR_CODE_112_DESC));
    }

    // Action - NOK and success scenario
    @ParameterizedTest
    @CsvSource({ "test006", "test002", "test004" })
    void test_action_nok_success(String testName, @Autowired MockMvc mvc) throws Exception {
        // bsb_before
        TrTableBsb bsb_before = getBsbBefore(testName);
        taxReportingRepository.save(bsb_before);

        // action
        String action = "NOK";

        timeProvider.setDateTime("20250413", "234509");

        // call review API
        ValidationResults validationResults = callReviewAPI(bsb_before, action, mvc);
        String actualMeldeStatus = validationResults.getValidationResults().get(1).getMessage();

        // bsb_after
        TrTableBsb bsb_after = getBsbAfter(testName);
        String expectedMeldeStatus = bsb_after.getMeldeStatus();

        // Assertion
        assertEquals(expectedMeldeStatus, actualMeldeStatus);

        journaling.assertJournaling(getBsbBefore(testName), "UC06", "TEST_NOK_RECORD_" + testName.toUpperCase());
    }

    // Action - NOK and failure scenario
    @ParameterizedTest
    @CsvSource({ "test006", "test002", "test004" })
    void test_action_nok_failure(String testName, @Autowired MockMvc mvc) throws Exception {
        // bsb_before
        TrTableBsb bsb_before = getBsbBefore(testName);
        bsb_before.setStatus("X"); // Updated to STATUS-X
        taxReportingRepository.save(bsb_before);

        // action
        String action = "NOK";

        // call review API
        ValidationResults validationResults = callReviewAPI(bsb_before, action, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        // Assertion
        assertTrue(actualMessage.contains(ValidationMessage.ERROR_CODE_112_DESC));
    }

    // Invalid action
    @Test
    void test_invalid_action(@Autowired MockMvc mvc) throws Exception {
        // bsb_before
        TrTableBsb bsb_before = getBsbBefore("test001");
        taxReportingRepository.save(bsb_before);

        // action
        String action = "XXX";

        // call review API
        ValidationResults validationResults = callReviewAPI(bsb_before, action, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        // Assertion
        assertTrue(actualMessage.contains("Valid values are OK or NOK"));
    }

    // Invalid bsb record
    @Test
    void test_invalid_bsb_record(@Autowired MockMvc mvc) throws Exception {
        // bsb_before
        TrTableBsb bsb_before = getBsbBefore("test001");

        // action
        String action = "OK";

        // call review API
        ValidationResults validationResults = callReviewAPI(bsb_before, action, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        // Assertion
        assertTrue(actualMessage.contains("No Tax Reports Records Found"));
    }

    // Invalid bsb status for review
    @Test
    void test_invalid_bsb_status_for_review(@Autowired MockMvc mvc) throws Exception {
        // bsb_before
        TrTableBsb bsb_before = getBsbBefore("test001");
        bsb_before.setStatus("H");
        taxReportingRepository.save(bsb_before);

        // action
        String action = "OK";

        // call review API
        ValidationResults validationResults = callReviewAPI(bsb_before, action, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        // Assertion
        assertTrue(actualMessage.contains(ValidationMessage.ERROR_CODE_112_DESC));
    }

    private TrTableBsb getBsbBefore(String testName) throws JsonProcessingException {
        TrTableBsb bsb_before = null;
        switch (testName) {
            case "test001":
                bsb_before = bsbList_test001_before.get(0); // STATUS-B, MELDE_STATUS=01
                break;

            case "test003":
                bsb_before = bsbList_test003_before.get(1); // STATUS-K, MELDE_STATUS=03
                break;

            case "test005":
                bsb_before = bsbList_test005_before.get(2); // STATUS-S, MELDE_STATUS=02
                break;

            case "test006":
                bsb_before = bsbList_test006_before.get(0); // STATUS-B, MELDE_STATUS=01
                break;

            case "test002":
                bsb_before = bsbList_test002_before.get(1); // STATUS-K, MELDE_STATUS=03
                break;

            case "test004":
                bsb_before = bsbList_test004_before.get(2); // STATUS-S, MELDE_STATUS=02
                break;
        }
        return cloneBsbDeep(bsb_before);
    }

    private TrTableBsb getBsbAfter(String testName) throws JsonProcessingException {
        TrTableBsb bsb_after = null;
        switch (testName) {
            case "test001":
                bsb_after = bsbList_test001_after.get(0); // STATUS-B, MELDE_STATUS=04
                break;

            case "test003":
                bsb_after = bsbList_test003_after.get(1); // STATUS-K, MELDE_STATUS=06
                break;

            case "test005":
                bsb_after = bsbList_test005_after.get(2); // STATUS-S, MELDE_STATUS=05
                break;

            case "test006":
                bsb_after = bsbList_test006_after.get(0); // STATUS-B, MELDE_STATUS=00
                break;

            case "test002":
                bsb_after = bsbList_test002_after.get(1); // STATUS-K, MELDE_STATUS=00
                break;

            case "test004":
                bsb_after = bsbList_test004_after.get(2); // STATUS-S, MELDE_STATUS=00
                break;
        }
        return cloneBsbDeep(bsb_after);
    }

    private ValidationResults callReviewAPI(TrTableBsb bsb_before, String action, MockMvc mvc) throws Exception {
        // Get Values from bsb_before
        TrTableBsbPK bsbPK = bsb_before.getTrTableBsbPK();
        String mandSl = bsbPK.getMandSl();
        long keyMeldejahr = bsbPK.getKeyMeldejahr();
        String keyMuster = bsbPK.getKeyMuster();
        long keyLaufnummer = bsbPK.getKeyLaufnummer();
        long keySysDatum = bsbPK.getKeySysDatum();
        long keyUhrzeit = bsbPK.getKeyUhrzeit();
        String keyIdNr = bsbPK.getKeyIdNr();

        // Send Request to API
        ValidationResults validationResults = this.reviewBsbRequest(mandSl, keyMeldejahr, keyMuster, keyLaufnummer,
                keySysDatum, keyUhrzeit, keyIdNr, action, mvc);

        return validationResults;
    }
}
