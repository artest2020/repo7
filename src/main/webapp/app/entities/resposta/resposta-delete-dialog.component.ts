import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResposta } from 'app/shared/model/resposta.model';
import { RespostaService } from './resposta.service';

@Component({
  templateUrl: './resposta-delete-dialog.component.html'
})
export class RespostaDeleteDialogComponent {
  resposta?: IResposta;

  constructor(protected respostaService: RespostaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.respostaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('respostaListModification');
      this.activeModal.close();
    });
  }
}
