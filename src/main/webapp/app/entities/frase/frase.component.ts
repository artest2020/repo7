import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFrase } from 'app/shared/model/frase.model';
import { FraseService } from './frase.service';
import { FraseDeleteDialogComponent } from './frase-delete-dialog.component';

@Component({
  selector: 'jhi-frase',
  templateUrl: './frase.component.html'
})
export class FraseComponent implements OnInit, OnDestroy {
  frases?: IFrase[];
  eventSubscriber?: Subscription;

  constructor(protected fraseService: FraseService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.fraseService.query().subscribe((res: HttpResponse<IFrase[]>) => {
      this.frases = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFrases();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFrase): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFrases(): void {
    this.eventSubscriber = this.eventManager.subscribe('fraseListModification', () => this.loadAll());
  }

  delete(frase: IFrase): void {
    const modalRef = this.modalService.open(FraseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.frase = frase;
  }
}
