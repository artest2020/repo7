import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEnunciado, Enunciado } from 'app/shared/model/enunciado.model';
import { EnunciadoService } from './enunciado.service';

@Component({
  selector: 'jhi-enunciado-update',
  templateUrl: './enunciado-update.component.html'
})
export class EnunciadoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    descricao: []
  });

  constructor(protected enunciadoService: EnunciadoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enunciado }) => {
      this.updateForm(enunciado);
    });
  }

  updateForm(enunciado: IEnunciado): void {
    this.editForm.patchValue({
      id: enunciado.id,
      descricao: enunciado.descricao
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enunciado = this.createFromForm();
    if (enunciado.id !== undefined) {
      this.subscribeToSaveResponse(this.enunciadoService.update(enunciado));
    } else {
      this.subscribeToSaveResponse(this.enunciadoService.create(enunciado));
    }
  }

  private createFromForm(): IEnunciado {
    return {
      ...new Enunciado(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnunciado>>): void {
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
