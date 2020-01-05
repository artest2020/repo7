import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPergunta } from 'app/shared/model/pergunta.model';
import { PerguntaService } from './pergunta.service';
import { PerguntaDeleteDialogComponent } from './pergunta-delete-dialog.component';

@Component({
  selector: 'jhi-pergunta',
  templateUrl: './pergunta.component.html'
})
export class PerguntaComponent implements OnInit, OnDestroy {
  perguntas?: IPergunta[];
  eventSubscriber?: Subscription;

  constructor(protected perguntaService: PerguntaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.perguntaService.query().subscribe((res: HttpResponse<IPergunta[]>) => {
      this.perguntas = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPerguntas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPergunta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPerguntas(): void {
    this.eventSubscriber = this.eventManager.subscribe('perguntaListModification', () => this.loadAll());
  }

  delete(pergunta: IPergunta): void {
    const modalRef = this.modalService.open(PerguntaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pergunta = pergunta;
  }
}
