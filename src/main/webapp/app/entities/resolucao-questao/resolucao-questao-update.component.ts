import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IResolucaoQuestao, ResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';
import { ResolucaoQuestaoService } from './resolucao-questao.service';

@Component({
  selector: 'jhi-resolucao-questao-update',
  templateUrl: './resolucao-questao-update.component.html'
})
export class ResolucaoQuestaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dataHoraInicio: [],
    dataHoraFim: []
  });

  constructor(
    protected resolucaoQuestaoService: ResolucaoQuestaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resolucaoQuestao }) => {
      this.updateForm(resolucaoQuestao);
    });
  }

  updateForm(resolucaoQuestao: IResolucaoQuestao): void {
    this.editForm.patchValue({
      id: resolucaoQuestao.id,
      dataHoraInicio: resolucaoQuestao.dataHoraInicio != null ? resolucaoQuestao.dataHoraInicio.format(DATE_TIME_FORMAT) : null,
      dataHoraFim: resolucaoQuestao.dataHoraFim != null ? resolucaoQuestao.dataHoraFim.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resolucaoQuestao = this.createFromForm();
    if (resolucaoQuestao.id !== undefined) {
      this.subscribeToSaveResponse(this.resolucaoQuestaoService.update(resolucaoQuestao));
    } else {
      this.subscribeToSaveResponse(this.resolucaoQuestaoService.create(resolucaoQuestao));
    }
  }

  private createFromForm(): IResolucaoQuestao {
    return {
      ...new ResolucaoQuestao(),
      id: this.editForm.get(['id'])!.value,
      dataHoraInicio:
        this.editForm.get(['dataHoraInicio'])!.value != null
          ? moment(this.editForm.get(['dataHoraInicio'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataHoraFim:
        this.editForm.get(['dataHoraFim'])!.value != null ? moment(this.editForm.get(['dataHoraFim'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResolucaoQuestao>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
