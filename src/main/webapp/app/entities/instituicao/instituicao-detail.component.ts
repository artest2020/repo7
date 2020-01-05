import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstituicao } from 'app/shared/model/instituicao.model';

@Component({
  selector: 'jhi-instituicao-detail',
  templateUrl: './instituicao-detail.component.html'
})
export class InstituicaoDetailComponent implements OnInit {
  instituicao: IInstituicao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instituicao }) => {
      this.instituicao = instituicao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
