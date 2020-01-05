package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class FraseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Frase.class);
        Frase frase1 = new Frase();
        frase1.setId(1L);
        Frase frase2 = new Frase();
        frase2.setId(frase1.getId());
        assertThat(frase1).isEqualTo(frase2);
        frase2.setId(2L);
        assertThat(frase1).isNotEqualTo(frase2);
        frase1.setId(null);
        assertThat(frase1).isNotEqualTo(frase2);
    }
}
