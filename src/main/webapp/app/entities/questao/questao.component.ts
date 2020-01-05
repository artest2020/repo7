import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestao } from 'app/shared/model/questao.model';
import { QuestaoService } from './questao.service';
import { QuestaoDeleteDialogComponent } from './questao-delete-dialog.component';

@Component({
  selector: 'jhi-questao',
  templateUrl: './questao.component.html'
})
export class QuestaoComponent implements OnInit, OnDestroy {
  questaos?: IQuestao[];
  eventSubscriber?: Subscription;

  constructor(protected questaoService: QuestaoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.questaoService.query().subscribe((res: HttpResponse<IQuestao[]>) => {
      this.questaos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('questaoListModification', () => this.loadAll());
  }

  delete(questao: IQuestao): void {
    const modalRef = this.modalService.open(QuestaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questao = questao;
  }
}
