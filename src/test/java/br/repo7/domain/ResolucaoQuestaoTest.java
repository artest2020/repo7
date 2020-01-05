package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class ResolucaoQuestaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResolucaoQuestao.class);
        ResolucaoQuestao resolucaoQuestao1 = new ResolucaoQuestao();
        resolucaoQuestao1.setId(1L);
        ResolucaoQuestao resolucaoQuestao2 = new ResolucaoQuestao();
        resolucaoQuestao2.setId(resolucaoQuestao1.getId());
        assertThat(resolucaoQuestao1).isEqualTo(resolucaoQuestao2);
        resolucaoQuestao2.setId(2L);
        assertThat(resolucaoQuestao1).isNotEqualTo(resolucaoQuestao2);
        resolucaoQuestao1.setId(null);
        assertThat(resolucaoQuestao1).isNotEqualTo(resolucaoQuestao2);
    }
}
