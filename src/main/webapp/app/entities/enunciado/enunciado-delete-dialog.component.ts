import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnunciado } from 'app/shared/model/enunciado.model';
import { EnunciadoService } from './enunciado.service';

@Component({
  templateUrl: './enunciado-delete-dialog.component.html'
})
export class EnunciadoDeleteDialogComponent {
  enunciado?: IEnunciado;

  constructor(protected enunciadoService: EnunciadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enunciadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enunciadoListModification');
      this.activeModal.close();
    });
  }
}
