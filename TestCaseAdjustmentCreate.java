package functional;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.util.CommonConstants;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCaseAdjustmentCreate extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_B_before;
    private List<TrTableBsb> bsbList_B_after;
    private List<TrTableBsb> bsbList_B_before_t2;
    private List<TrTableBsb> bsbList_B_before_t4;
    private List<TrTableBsb> bsbList_B_before_t5;
    private List<TrTableBsb> bsbList_B_before_t6;
    private List<TrTableBsb> bsbList_B_before_t7;
    private List<TrTableBsb> bsbList_B_before_t8;

    TrTableBsb bsb_before, bsb_after;

    @Autowired
    Journaling journaling;

    @BeforeAll
    void setupClass() {
        String filename = "Testcases_UC10.xlsx";
        bsbList_B_before = this.getTestCaseData("TEST001.before", filename);
        bsbList_B_after = this.getTestCaseData("TEST001.after", filename);
        bsbList_B_before_t2 = this.getTestCaseData("TEST002.before", filename);
        bsbList_B_before_t4 = this.getTestCaseData("TEST004.before", filename);
        bsbList_B_before_t5 = this.getTestCaseData("TEST005.before", filename);
        bsbList_B_before_t6 = this.getTestCaseData("TEST006.before", filename);
        bsbList_B_before_t7 = this.getTestCaseData("TEST007.before", filename);
        bsbList_B_before_t8 = this.getTestCaseData("TEST008.before", filename);

        timeProvider.stopTime();
    }

    @Test
    void test_that_repository_finds_the_correct_previous_object() {
        saveBsbList(bsbList_B_before);
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_B_before.get(1));

        String mandsl = StringUtils.rightPad(baseBsb.getTrTableBsbPK().getMandSl(), CommonConstants.MAND_SL_LENGTH);
        String keyIdNr = StringUtils.rightPad(baseBsb.getTrTableBsbPK().getKeyIdNr(), CommonConstants.KEY_ID_NR_LENGTH);
        String keyMuster = StringUtils.rightPad(baseBsb.getTrTableBsbPK().getKeyMuster(),
                CommonConstants.KEY_MUSTER_LENGTH);

        List<TrTableBsb> previousList = new ArrayList<>(taxReportingRepository.findByKeyNokeySysDatumNorUhrzeit(mandsl,
                keyIdNr, baseBsb.getTrTableBsbPK().getKeyMeldejahr(), keyMuster,
                baseBsb.getTrTableBsbPK().getKeyLaufnummer()));

        assertEquals(1, previousList.size());
        TrTableBsb previousBsb = previousList.get(0);
        TrTableBsbPK prevPK = previousBsb.getTrTableBsbPK();
        TrTableBsbPK currentPK = baseBsb.getTrTableBsbPK();

        assertNotEquals(prevPK, currentPK);
        assertEquals(prevPK.getMandSl(), currentPK.getMandSl());
        assertEquals(prevPK.getKeyIdNr(), currentPK.getKeyIdNr());
        assertEquals(prevPK.getKeyMeldejahr(), currentPK.getKeyMeldejahr());
        assertEquals(prevPK.getKeyMuster(), currentPK.getKeyMuster());
        assertEquals(prevPK.getKeyLaufnummer(), currentPK.getKeyLaufnummer());

        long prevTimeRepr = prevPK.getKeySysDatum() * 1_000_000 + prevPK.getKeyUhrzeit();
        long currentTimeRepr = currentPK.getKeySysDatum() * 1_000_000 + currentPK.getKeyUhrzeit();
        assertTrue(currentTimeRepr > prevTimeRepr);

    }

    @Test
    void test_that_status_has_changed_from_H_to_K(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_B_before);
        TrTableBsb baseBsb = cloneBsbDeep(bsbList_B_before.get(1));
        TrTableBsb bsb_after = cloneBsbDeep(bsbList_B_after.get(1));

        timeProvider.setDateTime("20250410", "171800");

        ResultActions response_baseBsb = createBsbRequest(baseBsb, mvc);

        TrTableBsb stored_bsb_after_request = findStoredBsb(baseBsb.getTrTableBsbPK());

        journaling.assertJournaling(baseBsb, "UC10", "TEST001");

        response_baseBsb.andExpect(status().isOk());

        assertEquals(bsb_after.getStatus(), stored_bsb_after_request.getStatus());

    }

    @Test
    void test_that_status_does_not_change_from_H_to_K(@Autowired MockMvc mvc) throws Exception {
        saveBsbList(bsbList_B_before_t6);

        timeProvider.setDateTime("20250410", "173500");

        TrTableBsb baseBsb = cloneBsbDeep(bsbList_B_before_t6.get(1));
        ResultActions resp = createBsbRequest(baseBsb, mvc);
        resp.andExpect(status().isOk());

        journaling.assertJournaling(bsbList_B_before_t6.get(1), "UC10", "TEST002");

    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_H_for_Bsb(@Autowired MockMvc mvc) throws Exception {
        List<TrTableBsb> bsbList = new ArrayList<>();
        for (TrTableBsb bsb : bsbList_B_before_t2) {
            bsbList.add(cloneBsbDeep(bsb));
        }

        bsbList.get(1).setStatus("K");

        this.saveBsbList(bsbList);

        TrTableBsb baseBsb = (bsbList.get(1));

        ResultActions resp = createBsbRequest(baseBsb, mvc);

        assertNotNull(resp);
        resp.andExpect(status().isBadRequest());
    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_for_subtables_no_H_for_subtables(@Autowired MockMvc mvc) throws Exception {
        this.saveBsbList(bsbList_B_before_t4);

        TrTableBsb baseBsb = bsbList_B_before_t4.get(1);

        timeProvider.setDateTime("20250410", "172600");

        ResultActions resp = createBsbRequest(baseBsb, mvc);

        assertNotNull(resp);
        resp.andExpect(status().isOk());

        journaling.assertJournaling(bsbList_B_before_t4.get(1), "UC10", "TEST003");
    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_status_H_and_bm1_keySatzart_H(@Autowired MockMvc mvc) throws Exception {
        this.saveBsbList(bsbList_B_before_t6);

        TrTableBsb baseBsb = bsbList_B_before_t6.get(1);

        timeProvider.setDateTime("20250410", "174400");

        ResultActions resActions = createBsbRequest(baseBsb, mvc);

        assertNotNull(baseBsb);
        resActions.andExpect(status().isOk());

        journaling.assertJournaling(bsbList_B_before_t6.get(1), "UC10", "TEST004");

    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_status_H_bm1_no_H(@Autowired MockMvc mvc) throws Exception {
        this.saveBsbList(bsbList_B_before_t7);
        TrTableBsb baseBsb = bsbList_B_before_t7.get(1);

        timeProvider.setDateTime("20250410", "165500");

        ResultActions resActions = createBsbRequest(baseBsb, mvc);
        assertNotNull(baseBsb);
        resActions.andExpect(status().isOk());

        journaling.assertJournaling(baseBsb, "UC10", "TEST005");

    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_status_H_bm3_no_H(@Autowired MockMvc mvc) throws Exception {
        this.saveBsbList(bsbList_B_before_t8);

        TrTableBsb baseBsb = bsbList_B_before_t8.get(1);
        timeProvider.setDateTime("20250410", "171100");
        ResultActions resActions = createBsbRequest(baseBsb, mvc);

        assertNotNull(baseBsb);
        resActions.andExpect(status().isOk());

        journaling.assertJournaling(baseBsb, "UC10", "TEST006");

    }

    @Test
    void test_status_B_Failed(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_B_before.get(1));
        bsb_before.setStatus("B");
        taxReportingRepository.save(bsb_before);
        this.storeChanges();
        ResultActions resActions = createBsbRequest(bsb_before, mvc);
        resActions.andExpect(status().isBadRequest());
    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_request(@Autowired MockMvc mvc, TestReporter reporter) throws Exception {
        reporter.publishEntry("Hello World!");
        TrTableBsb bsb1 = getFullBm1Example1();

        ResultActions resp = createBsbRequest(bsb1, mvc);
        assertNotNull(resp);
        resp.andExpect(status().isBadRequest());
    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_for_bm1_subtables(@Autowired MockMvc mvc) throws Exception {
        this.saveBsbList(bsbList_B_before_t6);
        TrTableBsb bsb_base = bsbList_B_before_t6.get(1);

        this.timeProvider.setDateTimeOfBsbBED(bsbList_B_before.get(1));
        ResultActions resp = createBsbRequest(bsb_base, mvc);
        assertNotNull(resp);

        resp.andExpect(status().isOk());

        journaling.assertJournaling(bsb_base, "UC10", "TEST007");
    }

    @Test
    @Tags({ @Tag("smoke"), })
    void smoke_test_for_bm3_subtables(@Autowired MockMvc mvc) throws Exception {
        this.saveBsbList(bsbList_B_before_t5);
        TrTableBsb bsb_base = cloneBsbDeep(bsbList_B_before_t5.get(1));

        timeProvider.setDateTime("20250410", "164600");

        ResultActions resp = createBsbRequest(bsb_base, mvc);

        assertNotNull(resp);
        resp.andExpect(status().isOk());

        journaling.assertJournaling(bsb_base, "UC10", "TEST008");
    }

    @Test
    void test_status_H_No_PreviousBsb_List(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_B_before.get(0));
        bsb_before.setStatus("H");
        taxReportingRepository.save(bsb_before);
        this.storeChanges();
        ResultActions resp = createBsbRequest(bsb_before, mvc);
        resp.andExpect(status().isBadRequest());
    }

    @Test
    void test_status_K_No_PreviousBsb_List(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_B_before.get(0));
        bsb_before.setStatus("K");
        taxReportingRepository.save(bsb_before);
        this.storeChanges();
        ResultActions resp = createBsbRequest(bsb_before, mvc);
        resp.andExpect(status().isBadRequest());
    }

    private ResultActions createBsbRequest(TrTableBsb currentBsb, MockMvc mvc) throws Exception {
        String mandSl = currentBsb.getTrTableBsbPK().getMandSl();
        String keyIdNr = currentBsb.getTrTableBsbPK().getKeyIdNr().trim();
        long keyMeldejahr = currentBsb.getTrTableBsbPK().getKeyMeldejahr();
        String keyMuster = currentBsb.getTrTableBsbPK().getKeyMuster().trim();
        long keyLaufnummer = currentBsb.getTrTableBsbPK().getKeyLaufnummer();
        long keySysDatum = currentBsb.getTrTableBsbPK().getKeySysDatum();
        long keyUhrzeit = currentBsb.getTrTableBsbPK().getKeyUhrzeit();

        MockHttpServletRequestBuilder request = post(
                "/jstb-correction/v1/adjustment?mandSl={0}&keyIdNr={1}&keyMeldejahr={2}&keyMuster={3}&keyLaufnummer={4}&keySysDatum={5}&keyUhrzeit={6}",
                mandSl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);
        request.characterEncoding("utf-8");

        return mvc.perform(request);
    }
}
