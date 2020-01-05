package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class EditalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Edital.class);
        Edital edital1 = new Edital();
        edital1.setId(1L);
        Edital edital2 = new Edital();
        edital2.setId(edital1.getId());
        assertThat(edital1).isEqualTo(edital2);
        edital2.setId(2L);
        assertThat(edital1).isNotEqualTo(edital2);
        edital1.setId(null);
        assertThat(edital1).isNotEqualTo(edital2);
    }
}
