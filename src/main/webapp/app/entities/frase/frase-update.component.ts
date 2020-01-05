import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IFrase, Frase } from 'app/shared/model/frase.model';
import { FraseService } from './frase.service';
import { IAlternativa } from 'app/shared/model/alternativa.model';
import { AlternativaService } from 'app/entities/alternativa/alternativa.service';

@Component({
  selector: 'jhi-frase-update',
  templateUrl: './frase-update.component.html'
})
export class FraseUpdateComponent implements OnInit {
  isSaving = false;

  alternativas: IAlternativa[] = [];

  editForm = this.fb.group({
    id: [],
    descricao: [],
    verdadeira: [],
    alternativa: []
  });

  constructor(
    protected fraseService: FraseService,
    protected alternativaService: AlternativaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ frase }) => {
      this.updateForm(frase);

      this.alternativaService
        .query()
        .pipe(
          map((res: HttpResponse<IAlternativa[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAlternativa[]) => (this.alternativas = resBody));
    });
  }

  updateForm(frase: IFrase): void {
    this.editForm.patchValue({
      id: frase.id,
      descricao: frase.descricao,
      verdadeira: frase.verdadeira,
      alternativa: frase.alternativa
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const frase = this.createFromForm();
    if (frase.id !== undefined) {
      this.subscribeToSaveResponse(this.fraseService.update(frase));
    } else {
      this.subscribeToSaveResponse(this.fraseService.create(frase));
    }
  }

  private createFromForm(): IFrase {
    return {
      ...new Frase(),
      id: this.editForm.get(['id'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      verdadeira: this.editForm.get(['verdadeira'])!.value,
      alternativa: this.editForm.get(['alternativa'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFrase>>): void {
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

  trackById(index: number, item: IAlternativa): any {
    return item.id;
  }
}
