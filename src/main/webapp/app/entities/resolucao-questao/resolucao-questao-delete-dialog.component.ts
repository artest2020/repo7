import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';
import { ResolucaoQuestaoService } from './resolucao-questao.service';

@Component({
  templateUrl: './resolucao-questao-delete-dialog.component.html'
})
export class ResolucaoQuestaoDeleteDialogComponent {
  resolucaoQuestao?: IResolucaoQuestao;

  constructor(
    protected resolucaoQuestaoService: ResolucaoQuestaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resolucaoQuestaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resolucaoQuestaoListModification');
      this.activeModal.close();
    });
  }
}
