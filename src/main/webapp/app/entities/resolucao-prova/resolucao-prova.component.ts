import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResolucaoProva } from 'app/shared/model/resolucao-prova.model';
import { ResolucaoProvaService } from './resolucao-prova.service';
import { ResolucaoProvaDeleteDialogComponent } from './resolucao-prova-delete-dialog.component';

@Component({
  selector: 'jhi-resolucao-prova',
  templateUrl: './resolucao-prova.component.html'
})
export class ResolucaoProvaComponent implements OnInit, OnDestroy {
  resolucaoProva?: IResolucaoProva[];
  eventSubscriber?: Subscription;

  constructor(
    protected resolucaoProvaService: ResolucaoProvaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.resolucaoProvaService.query().subscribe((res: HttpResponse<IResolucaoProva[]>) => {
      this.resolucaoProva = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInResolucaoProva();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IResolucaoProva): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInResolucaoProva(): void {
    this.eventSubscriber = this.eventManager.subscribe('resolucaoProvaListModification', () => this.loadAll());
  }

  delete(resolucaoProva: IResolucaoProva): void {
    const modalRef = this.modalService.open(ResolucaoProvaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resolucaoProva = resolucaoProva;
  }
}
