import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPergunta } from 'app/shared/model/pergunta.model';
import { PerguntaService } from './pergunta.service';

@Component({
  templateUrl: './pergunta-delete-dialog.component.html'
})
export class PerguntaDeleteDialogComponent {
  pergunta?: IPergunta;

  constructor(protected perguntaService: PerguntaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.perguntaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('perguntaListModification');
      this.activeModal.close();
    });
  }
}
