import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPergunta } from 'app/shared/model/pergunta.model';

@Component({
  selector: 'jhi-pergunta-detail',
  templateUrl: './pergunta-detail.component.html'
})
export class PerguntaDetailComponent implements OnInit {
  pergunta: IPergunta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pergunta }) => {
      this.pergunta = pergunta;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
