package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class QuestaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questao.class);
        Questao questao1 = new Questao();
        questao1.setId(1L);
        Questao questao2 = new Questao();
        questao2.setId(questao1.getId());
        assertThat(questao1).isEqualTo(questao2);
        questao2.setId(2L);
        assertThat(questao1).isNotEqualTo(questao2);
        questao1.setId(null);
        assertThat(questao1).isNotEqualTo(questao2);
    }
}
