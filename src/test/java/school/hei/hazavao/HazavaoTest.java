package school.hei.hazavao;

import org.junit.jupiter.api.Test;
import school.hei.hazavao.model.Hazavao;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HazavaoTest {

    Hazavao hazavao = new Hazavao();

    @Test
    void teny_malagasy_soa_should_have_definition() {
        String result = hazavao.apply("soa").toLowerCase();
        System.out.println("Réponse GPT : " + result);
        assertTrue(
                result.contains("tsara") ||
                        result.contains("fanazavana") ||
                        result.contains("fahendrena") ||
                        result.contains("fitiavana") ||
                        result.contains("fandriampahalemana"),
                "Tokony ahitana fanazavana ilay teny: " + result
        );
    }

}
