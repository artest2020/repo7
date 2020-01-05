import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnunciado } from 'app/shared/model/enunciado.model';

@Component({
  selector: 'jhi-enunciado-detail',
  templateUrl: './enunciado-detail.component.html'
})
export class EnunciadoDetailComponent implements OnInit {
  enunciado: IEnunciado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enunciado }) => {
      this.enunciado = enunciado;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
