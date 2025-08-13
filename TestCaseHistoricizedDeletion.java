package functional;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.service.taxreportinghistory.TaxReportingHistoricizedDeletionService;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCaseHistoricizedDeletion extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_H_before_t1;
    private List<TrTableBsb> bsbList_H_before_t2;
    private List<TrTableBsb> bsbList_H_before_t3;
    private List<TrTableBsb> bsbList_H_before_t4;

    @Autowired
    Journaling journaling;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TaxReportingHistoricizedDeletionService taxReportingHistoricizedDeletionService;

    @BeforeAll
    void setupClass() {
        String filename = "Testcases_UC14.xlsx";

        bsbList_H_before_t1 = this.getTestCaseData("TEST001.before", filename);
        bsbList_H_before_t2 = this.getTestCaseData("TEST002.before", filename);
        bsbList_H_before_t3 = this.getTestCaseData("TEST003.before", filename);
        bsbList_H_before_t4 = this.getTestCaseData("TEST004.before", filename);
        timeProvider.stopTime();

    }

    @Test
    void test_delete_history_BSB_with_BM1(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbBefore = cloneBsbDeep(bsbList_H_before_t1.get(0));

        taxReportingRepository.save(trTableBsbBefore);
        this.storeChanges();

        ValidationResults validationResults = this.deleteHistoricizedBsbRequest(trTableBsbBefore, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_112_DESC));

        journalingHistoryCreationTest(trTableBsbBefore, "TEST001");
    }

    @Test
    void test_delete_history_BSB_with_BM3(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbBefore = cloneBsbDeep(bsbList_H_before_t2.get(0));

        taxReportingRepository.save(trTableBsbBefore);
        this.storeChanges();

        ValidationResults validationResults = this.deleteHistoricizedBsbRequest(trTableBsbBefore, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ResponseMessage.MSG_CODE_112_DESC));

        journalingHistoryCreationTest(trTableBsbBefore, "TEST002");
    }

    @Test
    void test_delete_history_BSB_non_H_record(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbBefore = cloneBsbDeep(bsbList_H_before_t3.get(0));
        taxReportingRepository.save(trTableBsbBefore);
        this.storeChanges();

        ValidationResults validationResults = this.deleteHistoricizedBsbRequest(trTableBsbBefore, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ValidationMessage.ERROR_CODE_134_DESC));
    }

    @Test
    void test_delete_history_BSB_combination_other_than_H_record(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbBefore = cloneBsbDeep(bsbList_H_before_t4.get(0));
        taxReportingRepository.save(trTableBsbBefore);

        TrTableBsb trTableBsbWithStatusB = cloneBsbDeep(bsbList_H_before_t4.get(1));
        taxReportingRepository.save(trTableBsbWithStatusB);

        TrTableBsb trTableBsbWithStatusK = cloneBsbDeep(bsbList_H_before_t4.get(2));
        taxReportingRepository.save(trTableBsbWithStatusK);
        this.storeChanges();

        ValidationResults validationResults = this.deleteHistoricizedBsbRequest(trTableBsbBefore, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ValidationMessage.ERROR_CODE_135_DESC));
    }

    @Test
    void test_delete_history_BSB_Record_Not_found(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb trTableBsbBefore = cloneBsbDeep(bsbList_H_before_t1.get(0));

        ValidationResults validationResults = this.deleteHistoricizedBsbRequest(trTableBsbBefore, mvc);
        String actualMessage = validationResults.getValidationResults().get(0).getMessage();

        assertTrue(actualMessage.contains(ValidationMessage.ERROR_CODE_101_DESC));
    }

    private void journalingHistoryCreationTest(TrTableBsb trTableBsb, String testCase)
            throws ValidationResultsException, Exception {
        // TrTableJournaling trTableJournaling = taxReportingJournalingService
        // .journalingProcessExecutionHistory(trTableBsb, CommonConstants.HISTORICAL_DELETE_ACTION);
        // journaling.journalingTestProcess(trTableJournaling, "UC14", testCase);

        // The below utility can be used for generating the journalling test json
        // file(/src/test/Fixtures/resources/journaling/UC14)
        // 1.Make sure UC14 folder is created
        // 2.Comment above line journalingTestProcess
        // 3.unComment below line on generateJournalingJsonFileForBulkChanges
        // 4.Run test case - it will generate json files for the test cases.
        // 5.Once json files are generated. Comment line
        // generateJournalingJsonFileForBulkChanges and uncomment above line
        // journalingTestProcess

        // TestJournalingUtility.generateJournalingJsonFileForBulkChanges(trTableJournaling,"UC14",
        // testCase);
    }
}
