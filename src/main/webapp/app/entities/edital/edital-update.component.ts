import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEdital, Edital } from 'app/shared/model/edital.model';
import { EditalService } from './edital.service';

@Component({
  selector: 'jhi-edital-update',
  templateUrl: './edital-update.component.html'
})
export class EditalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    descricao: []
  });

  constructor(protected editalService: EditalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ edital }) => {
      this.updateForm(edital);
    });
  }

  updateForm(edital: IEdital): void {
    this.editForm.patchValue({
      id: edital.id,
      descricao: edital.descricao
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const edital = this.createFromForm();
    if (edital.id !== undefined) {
      this.subscribeToSaveResponse(this.editalService.update(edital));
    } else {
      this.subscribeToSaveResponse(this.editalService.create(edital));
    }
  }

  private createFromForm(): IEdital {
    return {
      ...new Edital(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEdital>>): void {
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
