package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class RespostaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resposta.class);
        Resposta resposta1 = new Resposta();
        resposta1.setId(1L);
        Resposta resposta2 = new Resposta();
        resposta2.setId(resposta1.getId());
        assertThat(resposta1).isEqualTo(resposta2);
        resposta2.setId(2L);
        assertThat(resposta1).isNotEqualTo(resposta2);
        resposta1.setId(null);
        assertThat(resposta1).isNotEqualTo(resposta2);
    }
}
