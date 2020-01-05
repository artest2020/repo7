import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from './instituicao.service';

@Component({
  templateUrl: './instituicao-delete-dialog.component.html'
})
export class InstituicaoDeleteDialogComponent {
  instituicao?: IInstituicao;

  constructor(
    protected instituicaoService: InstituicaoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.instituicaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('instituicaoListModification');
      this.activeModal.close();
    });
  }
}
