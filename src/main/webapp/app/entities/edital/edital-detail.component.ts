import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEdital } from 'app/shared/model/edital.model';

@Component({
  selector: 'jhi-edital-detail',
  templateUrl: './edital-detail.component.html'
})
export class EditalDetailComponent implements OnInit {
  edital: IEdital | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ edital }) => {
      this.edital = edital;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
