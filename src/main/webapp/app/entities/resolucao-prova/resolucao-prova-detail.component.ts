import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResolucaoProva } from 'app/shared/model/resolucao-prova.model';

@Component({
  selector: 'jhi-resolucao-prova-detail',
  templateUrl: './resolucao-prova-detail.component.html'
})
export class ResolucaoProvaDetailComponent implements OnInit {
  resolucaoProva: IResolucaoProva | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resolucaoProva }) => {
      this.resolucaoProva = resolucaoProva;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
