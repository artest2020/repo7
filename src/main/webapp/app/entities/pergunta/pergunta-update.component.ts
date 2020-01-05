import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPergunta, Pergunta } from 'app/shared/model/pergunta.model';
import { PerguntaService } from './pergunta.service';

@Component({
  selector: 'jhi-pergunta-update',
  templateUrl: './pergunta-update.component.html'
})
export class PerguntaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    descricao: []
  });

  constructor(protected perguntaService: PerguntaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pergunta }) => {
      this.updateForm(pergunta);
    });
  }

  updateForm(pergunta: IPergunta): void {
    this.editForm.patchValue({
      id: pergunta.id,
      descricao: pergunta.descricao
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pergunta = this.createFromForm();
    if (pergunta.id !== undefined) {
      this.subscribeToSaveResponse(this.perguntaService.update(pergunta));
    } else {
      this.subscribeToSaveResponse(this.perguntaService.create(pergunta));
    }
  }

  private createFromForm(): IPergunta {
    return {
      ...new Pergunta(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPergunta>>): void {
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
