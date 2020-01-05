import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnunciado } from 'app/shared/model/enunciado.model';
import { EnunciadoService } from './enunciado.service';
import { EnunciadoDeleteDialogComponent } from './enunciado-delete-dialog.component';

@Component({
  selector: 'jhi-enunciado',
  templateUrl: './enunciado.component.html'
})
export class EnunciadoComponent implements OnInit, OnDestroy {
  enunciados?: IEnunciado[];
  eventSubscriber?: Subscription;

  constructor(protected enunciadoService: EnunciadoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.enunciadoService.query().subscribe((res: HttpResponse<IEnunciado[]>) => {
      this.enunciados = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEnunciados();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEnunciado): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEnunciados(): void {
    this.eventSubscriber = this.eventManager.subscribe('enunciadoListModification', () => this.loadAll());
  }

  delete(enunciado: IEnunciado): void {
    const modalRef = this.modalService.open(EnunciadoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.enunciado = enunciado;
  }
}
