import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestao } from 'app/shared/model/questao.model';
import { QuestaoService } from './questao.service';

@Component({
  templateUrl: './questao-delete-dialog.component.html'
})
export class QuestaoDeleteDialogComponent {
  questao?: IQuestao;

  constructor(protected questaoService: QuestaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questaoListModification');
      this.activeModal.close();
    });
  }
}
