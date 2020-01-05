package br.repo7.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.repo7.web.rest.TestUtil;

public class ResolucaoProvaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResolucaoProva.class);
        ResolucaoProva resolucaoProva1 = new ResolucaoProva();
        resolucaoProva1.setId(1L);
        ResolucaoProva resolucaoProva2 = new ResolucaoProva();
        resolucaoProva2.setId(resolucaoProva1.getId());
        assertThat(resolucaoProva1).isEqualTo(resolucaoProva2);
        resolucaoProva2.setId(2L);
        assertThat(resolucaoProva1).isNotEqualTo(resolucaoProva2);
        resolucaoProva1.setId(null);
        assertThat(resolucaoProva1).isNotEqualTo(resolucaoProva2);
    }
}
