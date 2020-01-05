import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProva, Prova } from 'app/shared/model/prova.model';
import { ProvaService } from './prova.service';
import { IInstituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from 'app/entities/instituicao/instituicao.service';
import { IEdital } from 'app/shared/model/edital.model';
import { EditalService } from 'app/entities/edital/edital.service';

type SelectableEntity = IInstituicao | IEdital;

@Component({
  selector: 'jhi-prova-update',
  templateUrl: './prova-update.component.html'
})
export class ProvaUpdateComponent implements OnInit {
  isSaving = false;

  instituicaos: IInstituicao[] = [];

  editals: IEdital[] = [];

  editForm = this.fb.group({
    id: [],
    ano: [null, [Validators.required]],
    cidade: [],
    dataHoraInicio: [],
    dataHoraFim: [],
    instituicao: [null, Validators.required],
    edital: [null, Validators.required]
  });

  constructor(
    protected provaService: ProvaService,
    protected instituicaoService: InstituicaoService,
    protected editalService: EditalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prova }) => {
      this.updateForm(prova);

      this.instituicaoService
        .query({ filter: 'prova-is-null' })
        .pipe(
          map((res: HttpResponse<IInstituicao[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IInstituicao[]) => {
          if (!prova.instituicao || !prova.instituicao.id) {
            this.instituicaos = resBody;
          } else {
            this.instituicaoService
              .find(prova.instituicao.id)
              .pipe(
                map((subRes: HttpResponse<IInstituicao>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInstituicao[]) => {
                this.instituicaos = concatRes;
              });
          }
        });

      this.editalService
        .query({ filter: 'prova-is-null' })
        .pipe(
          map((res: HttpResponse<IEdital[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEdital[]) => {
          if (!prova.edital || !prova.edital.id) {
            this.editals = resBody;
          } else {
            this.editalService
              .find(prova.edital.id)
              .pipe(
                map((subRes: HttpResponse<IEdital>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEdital[]) => {
                this.editals = concatRes;
              });
          }
        });
    });
  }

  updateForm(prova: IProva): void {
    this.editForm.patchValue({
      id: prova.id,
      ano: prova.ano,
      cidade: prova.cidade,
      dataHoraInicio: prova.dataHoraInicio != null ? prova.dataHoraInicio.format(DATE_TIME_FORMAT) : null,
      dataHoraFim: prova.dataHoraFim != null ? prova.dataHoraFim.format(DATE_TIME_FORMAT) : null,
      instituicao: prova.instituicao,
      edital: prova.edital
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prova = this.createFromForm();
    if (prova.id !== undefined) {
      this.subscribeToSaveResponse(this.provaService.update(prova));
    } else {
      this.subscribeToSaveResponse(this.provaService.create(prova));
    }
  }

  private createFromForm(): IProva {
    return {
      ...new Prova(),
      id: this.editForm.get(['id'])!.value,
      ano: this.editForm.get(['ano'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      dataHoraInicio:
        this.editForm.get(['dataHoraInicio'])!.value != null
          ? moment(this.editForm.get(['dataHoraInicio'])!.value, DATE_TIME_FORMAT)
          : undefined,
      dataHoraFim:
        this.editForm.get(['dataHoraFim'])!.value != null ? moment(this.editForm.get(['dataHoraFim'])!.value, DATE_TIME_FORMAT) : undefined,
      instituicao: this.editForm.get(['instituicao'])!.value,
      edital: this.editForm.get(['edital'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProva>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
