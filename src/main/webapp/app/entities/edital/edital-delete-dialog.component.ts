import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEdital } from 'app/shared/model/edital.model';
import { EditalService } from './edital.service';

@Component({
  templateUrl: './edital-delete-dialog.component.html'
})
export class EditalDeleteDialogComponent {
  edital?: IEdital;

  constructor(protected editalService: EditalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.editalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('editalListModification');
      this.activeModal.close();
    });
  }
}
