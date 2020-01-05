import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlternativa } from 'app/shared/model/alternativa.model';

@Component({
  selector: 'jhi-alternativa-detail',
  templateUrl: './alternativa-detail.component.html'
})
export class AlternativaDetailComponent implements OnInit {
  alternativa: IAlternativa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alternativa }) => {
      this.alternativa = alternativa;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
