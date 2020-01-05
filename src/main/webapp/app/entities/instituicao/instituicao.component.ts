import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInstituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from './instituicao.service';
import { InstituicaoDeleteDialogComponent } from './instituicao-delete-dialog.component';

@Component({
  selector: 'jhi-instituicao',
  templateUrl: './instituicao.component.html'
})
export class InstituicaoComponent implements OnInit, OnDestroy {
  instituicaos?: IInstituicao[];
  eventSubscriber?: Subscription;

  constructor(
    protected instituicaoService: InstituicaoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.instituicaoService.query().subscribe((res: HttpResponse<IInstituicao[]>) => {
      this.instituicaos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInstituicaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInstituicao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInstituicaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('instituicaoListModification', () => this.loadAll());
  }

  delete(instituicao: IInstituicao): void {
    const modalRef = this.modalService.open(InstituicaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.instituicao = instituicao;
  }
}
