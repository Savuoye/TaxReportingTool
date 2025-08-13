package functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import helpers.Journaling;
import helpers.SubcutaneousTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCaseCreateCancellation extends SubcutaneousTest {
    private List<TrTableBsb> bsbList_example2_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_example2_after = new ArrayList<>();

    private List<TrTableBsb> bsbList_example20_before = new ArrayList<>();

    private List<TrTableBsb> bsbList_example20_after = new ArrayList<>();

    private TrTableBsb bsb_before, bsb_after;

    String bm1_H_result_Stornierung, bm1_H_after_Stornierung;

    @Autowired
    MockMvc mvc;

    @Autowired
    Journaling journaling;

    @BeforeAll
    void setupClass() {
        String filename = "Testcases_UC11.xlsx";

        bsbList_example2_before = this.getTestCaseData("TEST001.before", filename);
        bsbList_example2_after = this.getTestCaseData("TEST001.after", filename);

        bsbList_example20_before = this.getTestCaseData("TEST002.before", filename);
        bsbList_example20_after = this.getTestCaseData("TEST002.after", filename);

        timeProvider.stopTime();
    }

    @BeforeEach
    void resetProviders() {
        this.resetTestDoubles();
    }

    @Test
    void test_example20_ok(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_example20_before.get(2));
        bsb_after = cloneBsbDeep(bsbList_example20_after.get(2));

        for (TrTableBm1 bm1 : bsb_after.getTrTableBm1Set()) {
            if (bm1.getTrTableBm1PK().getKeySatzart().equals("H")) {
                bm1_H_after_Stornierung = bm1.getStornierung();
            }
        }

        for (TrTableBsb bsb : bsbList_example20_before) {
            taxReportingRepository.save(bsb);
            this.storeChanges();
        }

        timeProvider.setDateTime(bsb_after.getAnlageDatum().toString(),
                StringUtils.leftPad(bsb_after.getAnlageZeit().toString(), 6, "0"));
        usernameProvider.setAuthenticatedUserName(bsb_after.getAnlageErfasser());

        ResultActions resActions = createCancellation(bsb_before, mvc);

        resActions.andExpect(status().isOk());

        TrTableBsb result = findInDBByKey(bsb_before);

        for (TrTableBm1 bm1 : result.getTrTableBm1Set()) {
            if (bm1.getTrTableBm1PK().getKeySatzart().equals("H")) {
                bm1_H_result_Stornierung = bm1.getStornierung();
            }
        }

        assertEquals(bsb_after, result);
        assertEquals(bm1_H_after_Stornierung, bm1_H_result_Stornierung);

        journaling.assertJournaling(bsbList_example20_before.get(2), "UC11", "TEST001");
    }

    @Test
    void test_example20_noPreviousRefKmId(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_example20_before.get(2));
        bsb_after = cloneBsbDeep(bsbList_example20_after.get(2));

        bsbList_example20_before.get(1).setRefKmId("");

        for (TrTableBsb bsb : bsbList_example20_before) {
            taxReportingRepository.save(bsb);
            this.storeChanges();
        }

        timeProvider.setDateTime("20250410", "162900");

        ResultActions resActions = createCancellation(bsb_before, mvc);

        resActions.andExpect(status().isOk());

        TrTableBsb result = findInDBByKey(bsb_before);

        assertEquals(bsbList_example20_before.get(1).getKmId(), result.getRefKmId());

        journaling.assertJournaling(bsbList_example20_before.get(2), "UC11", "TEST002");
    }

    @Test
    void test_example2_ok(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_example2_before.get(0));
        bsb_after = cloneBsbDeep(bsbList_example2_after.get(0));

        taxReportingRepository.save(bsb_before);
        this.storeChanges();

        timeProvider.setDateTime(bsb_after.getAnlageDatum().toString(),
                StringUtils.leftPad(bsb_after.getAnlageZeit().toString(), 6, "0"));
        usernameProvider.setAuthenticatedUserName(bsb_after.getAnlageErfasser());

        ResultActions resActions = createCancellation(bsb_before, mvc);

        resActions.andExpect(status().isOk());

        TrTableBsb result = findInDBByKey(bsb_before);

        assertEquals(bsb_after, result);

        journaling.assertJournaling(bsb_before, "UC11", "TEST003");

    }

    @Test
    void test_status_B_failed(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_example20_before.get(0));
        bsb_after = cloneBsbDeep(bsbList_example20_after.get(0));

        taxReportingRepository.save(bsb_before);
        this.storeChanges();

        ResultActions resActions = createCancellation(bsb_before, mvc);

        resActions.andExpect(status().isBadRequest());
    }

    @Test
    void test_status_K_failed(@Autowired MockMvc mvc) throws Exception {
        bsb_before = cloneBsbDeep(bsbList_example20_before.get(1));
        bsb_after = cloneBsbDeep(bsbList_example20_after.get(1));

        taxReportingRepository.save(bsb_before);
        this.storeChanges();

        ResultActions resActions = createCancellation(bsb_before, mvc);

        resActions.andExpect(status().isBadRequest());
    }
}
