import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResolucaoProva } from 'app/shared/model/resolucao-prova.model';
import { ResolucaoProvaService } from './resolucao-prova.service';

@Component({
  templateUrl: './resolucao-prova-delete-dialog.component.html'
})
export class ResolucaoProvaDeleteDialogComponent {
  resolucaoProva?: IResolucaoProva;

  constructor(
    protected resolucaoProvaService: ResolucaoProvaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resolucaoProvaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resolucaoProvaListModification');
      this.activeModal.close();
    });
  }
}
