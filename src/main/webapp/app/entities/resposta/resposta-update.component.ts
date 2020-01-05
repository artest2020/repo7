import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IResposta, Resposta } from 'app/shared/model/resposta.model';
import { RespostaService } from './resposta.service';

@Component({
  selector: 'jhi-resposta-update',
  templateUrl: './resposta-update.component.html'
})
export class RespostaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    valorVF: [],
    valorTexto: [],
    valorN: [],
    valorZ: [],
    valorQ: []
  });

  constructor(protected respostaService: RespostaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resposta }) => {
      this.updateForm(resposta);
    });
  }

  updateForm(resposta: IResposta): void {
    this.editForm.patchValue({
      id: resposta.id,
      tipo: resposta.tipo,
      valorVF: resposta.valorVF,
      valorTexto: resposta.valorTexto,
      valorN: resposta.valorN,
      valorZ: resposta.valorZ,
      valorQ: resposta.valorQ
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resposta = this.createFromForm();
    if (resposta.id !== undefined) {
      this.subscribeToSaveResponse(this.respostaService.update(resposta));
    } else {
      this.subscribeToSaveResponse(this.respostaService.create(resposta));
    }
  }

  private createFromForm(): IResposta {
    return {
      ...new Resposta(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      valorVF: this.editForm.get(['valorVF'])!.value,
      valorTexto: this.editForm.get(['valorTexto'])!.value,
      valorN: this.editForm.get(['valorN'])!.value,
      valorZ: this.editForm.get(['valorZ'])!.value,
      valorQ: this.editForm.get(['valorQ'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResposta>>): void {
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
