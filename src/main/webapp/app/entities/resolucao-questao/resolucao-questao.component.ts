import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';
import { ResolucaoQuestaoService } from './resolucao-questao.service';
import { ResolucaoQuestaoDeleteDialogComponent } from './resolucao-questao-delete-dialog.component';

@Component({
  selector: 'jhi-resolucao-questao',
  templateUrl: './resolucao-questao.component.html'
})
export class ResolucaoQuestaoComponent implements OnInit, OnDestroy {
  resolucaoQuestaos?: IResolucaoQuestao[];
  eventSubscriber?: Subscription;

  constructor(
    protected resolucaoQuestaoService: ResolucaoQuestaoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.resolucaoQuestaoService.query().subscribe((res: HttpResponse<IResolucaoQuestao[]>) => {
      this.resolucaoQuestaos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInResolucaoQuestaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IResolucaoQuestao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInResolucaoQuestaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('resolucaoQuestaoListModification', () => this.loadAll());
  }

  delete(resolucaoQuestao: IResolucaoQuestao): void {
    const modalRef = this.modalService.open(ResolucaoQuestaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resolucaoQuestao = resolucaoQuestao;
  }
}
