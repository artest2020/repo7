import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProva } from 'app/shared/model/prova.model';
import { ProvaService } from './prova.service';
import { ProvaDeleteDialogComponent } from './prova-delete-dialog.component';

@Component({
  selector: 'jhi-prova',
  templateUrl: './prova.component.html'
})
export class ProvaComponent implements OnInit, OnDestroy {
  prova?: IProva[];
  eventSubscriber?: Subscription;

  constructor(protected provaService: ProvaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.provaService.query().subscribe((res: HttpResponse<IProva[]>) => {
      this.prova = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProva();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProva): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProva(): void {
    this.eventSubscriber = this.eventManager.subscribe('provaListModification', () => this.loadAll());
  }

  delete(prova: IProva): void {
    const modalRef = this.modalService.open(ProvaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prova = prova;
  }
}
