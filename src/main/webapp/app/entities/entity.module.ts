import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'instituicao',
        loadChildren: () => import('./instituicao/instituicao.module').then(m => m.Repo7InstituicaoModule)
      },
      {
        path: 'prova',
        loadChildren: () => import('./prova/prova.module').then(m => m.Repo7ProvaModule)
      },
      {
        path: 'edital',
        loadChildren: () => import('./edital/edital.module').then(m => m.Repo7EditalModule)
      },
      {
        path: 'questao',
        loadChildren: () => import('./questao/questao.module').then(m => m.Repo7QuestaoModule)
      },
      {
        path: 'enunciado',
        loadChildren: () => import('./enunciado/enunciado.module').then(m => m.Repo7EnunciadoModule)
      },
      {
        path: 'pergunta',
        loadChildren: () => import('./pergunta/pergunta.module').then(m => m.Repo7PerguntaModule)
      },
      {
        path: 'alternativa',
        loadChildren: () => import('./alternativa/alternativa.module').then(m => m.Repo7AlternativaModule)
      },
      {
        path: 'frase',
        loadChildren: () => import('./frase/frase.module').then(m => m.Repo7FraseModule)
      },
      {
        path: 'resposta',
        loadChildren: () => import('./resposta/resposta.module').then(m => m.Repo7RespostaModule)
      },
      {
        path: 'resolucao-prova',
        loadChildren: () => import('./resolucao-prova/resolucao-prova.module').then(m => m.Repo7ResolucaoProvaModule)
      },
      {
        path: 'resolucao-questao',
        loadChildren: () => import('./resolucao-questao/resolucao-questao.module').then(m => m.Repo7ResolucaoQuestaoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Repo7EntityModule {}
