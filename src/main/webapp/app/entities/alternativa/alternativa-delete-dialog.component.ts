import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlternativa } from 'app/shared/model/alternativa.model';
import { AlternativaService } from './alternativa.service';

@Component({
  templateUrl: './alternativa-delete-dialog.component.html'
})
export class AlternativaDeleteDialogComponent {
  alternativa?: IAlternativa;

  constructor(
    protected alternativaService: AlternativaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alternativaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alternativaListModification');
      this.activeModal.close();
    });
  }
}
