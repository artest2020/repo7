import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEdital } from 'app/shared/model/edital.model';
import { EditalService } from './edital.service';
import { EditalDeleteDialogComponent } from './edital-delete-dialog.component';

@Component({
  selector: 'jhi-edital',
  templateUrl: './edital.component.html'
})
export class EditalComponent implements OnInit, OnDestroy {
  editals?: IEdital[];
  eventSubscriber?: Subscription;

  constructor(protected editalService: EditalService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.editalService.query().subscribe((res: HttpResponse<IEdital[]>) => {
      this.editals = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEditals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEdital): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEditals(): void {
    this.eventSubscriber = this.eventManager.subscribe('editalListModification', () => this.loadAll());
  }

  delete(edital: IEdital): void {
    const modalRef = this.modalService.open(EditalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.edital = edital;
  }
}
