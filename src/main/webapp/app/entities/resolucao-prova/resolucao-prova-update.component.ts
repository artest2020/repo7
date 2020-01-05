import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IResolucaoProva, ResolucaoProva } from 'app/shared/model/resolucao-prova.model';
import { ResolucaoProvaService } from './resolucao-prova.service';

@Component({
  selector: 'jhi-resolucao-prova-update',
  templateUrl: './resolucao-prova-update.component.html'
})
export class ResolucaoProvaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dataHoraInicio: [],
    dataHoraFim: []
  });

  constructor(protected resolucaoProvaService: ResolucaoProvaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resolucaoProva }) => {
      this.updateForm(resolucaoProva);
    });
  }

  updateForm(resolucaoProva: IResolucaoProva): void {
    this.editForm.patchValue({
      id: resolucaoProva.id,
      dataHoraInicio: resolucaoProva.dataHoraInicio != null ? resolucaoProva.dataHoraInicio.format(DATE_TIME_FORMAT) : null,
      dataHoraFim: resolucaoProva.dataHoraFim != null ? resolucaoProva.dataHoraFim.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resolucaoProva = this.createFromForm();
    if (resolucaoProva.id !== undefined) {
      this.subscribeToSaveResponse(this.resolucaoProvaService.update(resolucaoProva));
    } else {
      this.subscribeToSaveResponse(this.resolucaoProvaService.create(resolucaoProva));
    }
  }

  private createFromForm(): IResolucaoProva {
    return {
      ...new ResolucaoProva(),
      id: this.editForm.get(['id'])!.value,
      dataHoraInicio:
        this.editForm.get(['dataHoraInicio'])!.value != null
          ? moment(this.editForm.get(['dataHoraInicio'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataHoraFim:
        this.editForm.get(['dataHoraFim'])!.value != null ? moment(this.editForm.get(['dataHoraFim'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResolucaoProva>>): void {
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
