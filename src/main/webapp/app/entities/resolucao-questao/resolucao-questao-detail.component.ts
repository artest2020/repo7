import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';

@Component({
  selector: 'jhi-resolucao-questao-detail',
  templateUrl: './resolucao-questao-detail.component.html'
})
export class ResolucaoQuestaoDetailComponent implements OnInit {
  resolucaoQuestao: IResolucaoQuestao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resolucaoQuestao }) => {
      this.resolucaoQuestao = resolucaoQuestao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
