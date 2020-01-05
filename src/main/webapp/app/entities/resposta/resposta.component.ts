import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResposta } from 'app/shared/model/resposta.model';
import { RespostaService } from './resposta.service';
import { RespostaDeleteDialogComponent } from './resposta-delete-dialog.component';

@Component({
  selector: 'jhi-resposta',
  templateUrl: './resposta.component.html'
})
export class RespostaComponent implements OnInit, OnDestroy {
  respostas?: IResposta[];
  eventSubscriber?: Subscription;

  constructor(protected respostaService: RespostaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.respostaService.query().subscribe((res: HttpResponse<IResposta[]>) => {
      this.respostas = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRespostas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IResposta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRespostas(): void {
    this.eventSubscriber = this.eventManager.subscribe('respostaListModification', () => this.loadAll());
  }

  delete(resposta: IResposta): void {
    const modalRef = this.modalService.open(RespostaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resposta = resposta;
  }
}
