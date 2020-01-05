import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlternativa } from 'app/shared/model/alternativa.model';
import { AlternativaService } from './alternativa.service';
import { AlternativaDeleteDialogComponent } from './alternativa-delete-dialog.component';

@Component({
  selector: 'jhi-alternativa',
  templateUrl: './alternativa.component.html'
})
export class AlternativaComponent implements OnInit, OnDestroy {
  alternativas?: IAlternativa[];
  eventSubscriber?: Subscription;

  constructor(
    protected alternativaService: AlternativaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.alternativaService.query().subscribe((res: HttpResponse<IAlternativa[]>) => {
      this.alternativas = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlternativas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlternativa): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlternativas(): void {
    this.eventSubscriber = this.eventManager.subscribe('alternativaListModification', () => this.loadAll());
  }

  delete(alternativa: IAlternativa): void {
    const modalRef = this.modalService.open(AlternativaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alternativa = alternativa;
  }
}
