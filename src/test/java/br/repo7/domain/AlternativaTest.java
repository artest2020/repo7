package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class AlternativaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alternativa.class);
        Alternativa alternativa1 = new Alternativa();
        alternativa1.setId(1L);
        Alternativa alternativa2 = new Alternativa();
        alternativa2.setId(alternativa1.getId());
        assertThat(alternativa1).isEqualTo(alternativa2);
        alternativa2.setId(2L);
        assertThat(alternativa1).isNotEqualTo(alternativa2);
        alternativa1.setId(null);
        assertThat(alternativa1).isNotEqualTo(alternativa2);
    }
}
