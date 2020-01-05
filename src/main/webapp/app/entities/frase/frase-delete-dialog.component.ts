import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFrase } from 'app/shared/model/frase.model';
import { FraseService } from './frase.service';

@Component({
  templateUrl: './frase-delete-dialog.component.html'
})
export class FraseDeleteDialogComponent {
  frase?: IFrase;

  constructor(protected fraseService: FraseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fraseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fraseListModification');
      this.activeModal.close();
    });
  }
}
