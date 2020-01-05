package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class EnunciadoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enunciado.class);
        Enunciado enunciado1 = new Enunciado();
        enunciado1.setId(1L);
        Enunciado enunciado2 = new Enunciado();
        enunciado2.setId(enunciado1.getId());
        assertThat(enunciado1).isEqualTo(enunciado2);
        enunciado2.setId(2L);
        assertThat(enunciado1).isNotEqualTo(enunciado2);
        enunciado1.setId(null);
        assertThat(enunciado1).isNotEqualTo(enunciado2);
    }
}
