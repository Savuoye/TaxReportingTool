package functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm1Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCasePatchData extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_before_t2 = new ArrayList<>();

    @SuppressWarnings("unused")
    private List<TrTableBsb> bsbList_before_t1 = new ArrayList<>();
    @SuppressWarnings("unused")
    private List<TrTableBsb> bsbList_after_t1 = new ArrayList<>();

    @SuppressWarnings("unused")
    private List<TrTableBsb> bm1List_B_before_t3 = new ArrayList<>();
    @SuppressWarnings("unused")
    private List<TrTableBsb> bm1List_B_after_t3 = new ArrayList<>();

    private List<TrTableBsb> bm1List_B_before_t4 = new ArrayList<>();
    private List<TrTableBsb> bm1List_B_after_t4 = new ArrayList<>();

    @Autowired
    Journaling journaling;

    @BeforeAll
    void setupClass() {
        bsbList_before_t2 = this.getTestCaseData("TEST002.before", "Testcases_UC04.xlsx");

        bsbList_before_t1 = this.getTestCaseData("TEST001.before", "Testcases_UC04.xlsx");
        bsbList_after_t1 = this.getTestCaseData("TEST001.after", "Testcases_UC04.xlsx");
        bm1List_B_before_t4 = this.getTestCaseData("TEST004.before", "Testcases_UC04.xlsx");
        bm1List_B_after_t4 = this.getTestCaseData("TEST004.after", "Testcases_UC04.xlsx");
        bm1List_B_before_t3 = this.getTestCaseData("TEST003.before", "Testcases_UC04.xlsx");
        bm1List_B_after_t3 = this.getTestCaseData("TEST003.after", "Testcases_UC04.xlsx");

        this.resetTestDoubles();
        this.timeProvider.stopTime();
    }

    @Test
    void test_original_json_ok(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb basebsb = cloneBsbDeep(bsbList_before_t2.get(0));
        this.taxReportingRepository.save(basebsb);

        this.storeChanges();

        String response = this.patchBsbRequest(basebsb, mvc);
        String message = JsonPath.read(response, "$.validationResults[0].affectedField");
        Integer modifiedFields = Integer.parseInt(message);
        assertEquals(0, modifiedFields);

        journaling.assertJournaling(basebsb, "UC04", "TEST001");
    }

    @Test
    void test_original_json_ok_modify_all_seven_fields(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_before_t2.get(0));
        taxReportingRepository.save(baseBsb);

        TrTableBsb updatedBsb = cloneBsbDeep(bsbList_before_t2.get(0));

        timeProvider.setDateTime("20250413", "221500");

        updatedBsb.setStatusKommentar("y");
        updatedBsb.setAufnehmEmail("y@mail.com");
        updatedBsb.setAufnehmGkOrt("y");
        updatedBsb.setAufnehmAuslPlz("y");
        updatedBsb.setAufnehmHsnr(BigDecimal.ONE);
        updatedBsb.setAufnehmName("y");
        updatedBsb.setAufnehmOrdnBegr("y");

        String response = this.patchBsbRequest(updatedBsb, mvc);
        String message = JsonPath.read(response, "$.validationResults[0].affectedField");
        Integer modifiedFields = Integer.parseInt(message);

        assertEquals(5, modifiedFields);

        journaling.assertJournaling(baseBsb, "UC04", "TEST002");
    }

    @Test
    void test_not_changed_user_all_seven_fields_updated(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_before_t2.get(0));
        baseBsb.setAenderErfasser("clerka");
        taxReportingRepository.save(baseBsb);

        timeProvider.setDateTime("20250413", "084756");

        TrTableBsb updatedBsb = cloneBsbDeep(baseBsb);

        updatedBsb.setStatusKommentar("y");
        updatedBsb.setAufnehmEmail("y@mail.com");
        updatedBsb.setAufnehmGkOrt("y");
        updatedBsb.setAufnehmAuslPlz("y");
        updatedBsb.setAufnehmHsnr(BigDecimal.ONE);
        updatedBsb.setAufnehmName("y");
        updatedBsb.setAufnehmOrdnBegr("y");

        String response = this.patchBsbRequest(updatedBsb, mvc);
        String message = JsonPath.read(response, "$.validationResults[0].affectedField");
        Integer modifiedFields = Integer.parseInt(message);

        assertEquals(4, modifiedFields);

        journaling.assertJournaling(baseBsb, "UC04", "TEST003");
    }

    @Test
    void test_not_changed_user_and_aenderDatum_all_seven_fields_updated(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_before_t2.get(0));

        baseBsb.setAenderErfasser("clerka");
        timeProvider.setDateTime("20250413", "213400");
        baseBsb.setAenderDatum(new BigDecimal(timeProvider.getCurrentTime("yyyyMMdd")));
        taxReportingRepository.save(baseBsb);

        TrTableBsb updatedBsb = cloneBsbDeep(baseBsb);

        updatedBsb.setStatusKommentar("y");
        updatedBsb.setAufnehmEmail("y@mail.com");
        updatedBsb.setAufnehmGkOrt("y");
        updatedBsb.setAufnehmAuslPlz("y");
        updatedBsb.setAufnehmHsnr(BigDecimal.ONE);
        updatedBsb.setAufnehmName("y");
        updatedBsb.setAufnehmOrdnBegr("y");

        String response = this.patchBsbRequest(updatedBsb, mvc);
        String message = JsonPath.read(response, "$.validationResults[0].affectedField");
        Integer modifiedFields = Integer.parseInt(message);

        assertEquals(3, modifiedFields);

        journaling.assertJournaling(baseBsb, "UC04", "TEST004");
    }

    @Test
    void test_only_one_programmatically_field_has_changed_all_seven_fields_updated(@Autowired MockMvc mvc)
            throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_before_t2.get(0));
        this.setDateTimeOfBsb(baseBsb);
        baseBsb.setAenderErfasser("clerka");
        baseBsb.setAenderDatum(new BigDecimal(timeProvider.getCurrentTime("yyyyMMdd")));
        baseBsb.setAenderZeit(new BigDecimal(timeProvider.getCurrentTime("HHmmss")));
        taxReportingRepository.save(baseBsb);

        TrTableBsb updatedBsb = cloneBsbDeep(baseBsb);

        updatedBsb.setStatusKommentar("y");
        updatedBsb.setAufnehmEmail("y@mail.com");
        updatedBsb.setAufnehmGkOrt("y");
        updatedBsb.setAufnehmAuslPlz("y");
        updatedBsb.setAufnehmHsnr(BigDecimal.ONE);
        updatedBsb.setAufnehmName("y");
        updatedBsb.setAufnehmOrdnBegr("y");

        String response = this.patchBsbRequest(updatedBsb, mvc);
        String message = JsonPath.read(response, "$.validationResults[0].affectedField");
        Integer modifiedFields = Integer.parseInt(message);

        assertEquals(2, modifiedFields);

        journaling.assertJournaling(baseBsb, "UC04", "TEST005");

    }

    @Test
    void test_no_programatical_field_and_all_seven_fields_updated(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_before_t2.get(0));
        baseBsb.setAenderErfasser("clerka");

        setDateTimeOfBsb(baseBsb);

        baseBsb.setBedbsbTimestamp(timeProvider.getCurrentTime("yyyyMMddHHmmssSS"));
        baseBsb.setAenderDatum(new BigDecimal(timeProvider.getCurrentTime("yyyyMMdd")));
        baseBsb.setAenderZeit(new BigDecimal(timeProvider.getCurrentTime("HHmmss")));
        taxReportingRepository.save(baseBsb);

        TrTableBsb updatedBsb = cloneBsbDeep(baseBsb);

        updatedBsb.setStatusKommentar("y");
        updatedBsb.setAufnehmEmail("y@mail.com");
        updatedBsb.setAufnehmGkOrt("y");
        updatedBsb.setAufnehmAuslPlz("y");
        updatedBsb.setAufnehmHsnr(BigDecimal.ONE);
        updatedBsb.setAufnehmName("y");
        updatedBsb.setAufnehmOrdnBegr("y");

        String response = this.patchBsbRequest(updatedBsb, mvc);
        String message = JsonPath.read(response, "$.validationResults[0].affectedField");
        Integer modifiedFields = Integer.parseInt(message);
        assertEquals(1, modifiedFields);

        journaling.assertJournaling(baseBsb, "UC04", "TEST006");
    }

    @Test
    void test_bm1_aam_created(@Autowired MockMvc mvc) throws Exception {
        TrTableBsb bsb_before = bm1List_B_before_t4.get(0);
        TrTableBsb bsb_after = bm1List_B_after_t4.get(0);

        timeProvider.setDateTime("20250413", "213055");

        taxReportingRepository.save(bsb_before);
        this.storeChanges();

        TrTableBm1Aam aamPendingCreation = null;

        for (TrTableBm1 bm1 : bsb_after.getTrTableBm1Set()) {
            for (TrTableBm1Aam aam : bm1.getTrTableBm1AamSet()) {
                ObjectMapper objectMapper = new ObjectMapper();
                aamPendingCreation = objectMapper.readValue(objectMapper.writeValueAsString(aam), TrTableBm1Aam.class);
                aam.getTrTableAamPK().setKeyAbrechNr(null);
            }
        }

        this.patchBsbRequest(bsb_after, mvc);

        TrTableBsb stored_bsb_after_request = this.findInDBByKey(bsb_after);

        for (TrTableBm1 bm1 : stored_bsb_after_request.getTrTableBm1Set()) {
            if (bm1.getTrTableBm1PK().getKeySatzart().equals("H")) {
                assertEquals(0, bm1.getTrTableBm1AamSet().size());
                continue;
            }
            assertEquals(1, bm1.getTrTableBm1AamSet().size());
            TrTableBm1Aam storedAam = bm1.getTrTableBm1AamSet().iterator().next();
            assertEquals(aamPendingCreation, storedAam);
        }

        journaling.assertJournaling(bsb_before, "UC04", "TEST007");
    }

    protected String patchBsbRequest(TrTableBsb bsb, MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder request = put("/jstb-correction/v1");
        request.accept(MediaType.APPLICATION_JSON_VALUE);
        request.contentType(MediaType.APPLICATION_JSON_VALUE);
        request.characterEncoding("utf-8");
        request.content(new ObjectMapper().writeValueAsString(bsb));
        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();
        this.storeChanges();
        return result.getResponse().getContentAsString();
    }
}
