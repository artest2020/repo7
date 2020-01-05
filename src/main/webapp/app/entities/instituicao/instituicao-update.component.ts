import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInstituicao, Instituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from './instituicao.service';

@Component({
  selector: 'jhi-instituicao-update',
  templateUrl: './instituicao-update.component.html'
})
export class InstituicaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    sigla: []
  });

  constructor(protected instituicaoService: InstituicaoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instituicao }) => {
      this.updateForm(instituicao);
    });
  }

  updateForm(instituicao: IInstituicao): void {
    this.editForm.patchValue({
      id: instituicao.id,
      nome: instituicao.nome,
      sigla: instituicao.sigla
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const instituicao = this.createFromForm();
    if (instituicao.id !== undefined) {
      this.subscribeToSaveResponse(this.instituicaoService.update(instituicao));
    } else {
      this.subscribeToSaveResponse(this.instituicaoService.create(instituicao));
    }
  }

  private createFromForm(): IInstituicao {
    return {
      ...new Instituicao(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      sigla: this.editForm.get(['sigla'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstituicao>>): void {
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
