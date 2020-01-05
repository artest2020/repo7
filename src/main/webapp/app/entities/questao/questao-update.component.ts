import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IQuestao, Questao } from 'app/shared/model/questao.model';
import { QuestaoService } from './questao.service';
import { IEnunciado } from 'app/shared/model/enunciado.model';
import { EnunciadoService } from 'app/entities/enunciado/enunciado.service';
import { IPergunta } from 'app/shared/model/pergunta.model';
import { PerguntaService } from 'app/entities/pergunta/pergunta.service';
import { IProva } from 'app/shared/model/prova.model';
import { ProvaService } from 'app/entities/prova/prova.service';

type SelectableEntity = IEnunciado | IPergunta | IProva;

@Component({
  selector: 'jhi-questao-update',
  templateUrl: './questao-update.component.html'
})
export class QuestaoUpdateComponent implements OnInit {
  isSaving = false;

  enunciados: IEnunciado[] = [];

  perguntas: IPergunta[] = [];

  prova: IProva[] = [];

  editForm = this.fb.group({
    id: [],
    ordem: [],
    enunciado: [null, Validators.required],
    pergunta: [null, Validators.required],
    prova: []
  });

  constructor(
    protected questaoService: QuestaoService,
    protected enunciadoService: EnunciadoService,
    protected perguntaService: PerguntaService,
    protected provaService: ProvaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questao }) => {
      this.updateForm(questao);

      this.enunciadoService
        .query({ filter: 'questao-is-null' })
        .pipe(
          map((res: HttpResponse<IEnunciado[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEnunciado[]) => {
          if (!questao.enunciado || !questao.enunciado.id) {
            this.enunciados = resBody;
          } else {
            this.enunciadoService
              .find(questao.enunciado.id)
              .pipe(
                map((subRes: HttpResponse<IEnunciado>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEnunciado[]) => {
                this.enunciados = concatRes;
              });
          }
        });

      this.perguntaService
        .query({ filter: 'questao-is-null' })
        .pipe(
          map((res: HttpResponse<IPergunta[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPergunta[]) => {
          if (!questao.pergunta || !questao.pergunta.id) {
            this.perguntas = resBody;
          } else {
            this.perguntaService
              .find(questao.pergunta.id)
              .pipe(
                map((subRes: HttpResponse<IPergunta>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPergunta[]) => {
                this.perguntas = concatRes;
              });
          }
        });

      this.provaService
        .query()
        .pipe(
          map((res: HttpResponse<IProva[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProva[]) => (this.prova = resBody));
    });
  }

  updateForm(questao: IQuestao): void {
    this.editForm.patchValue({
      id: questao.id,
      ordem: questao.ordem,
      enunciado: questao.enunciado,
      pergunta: questao.pergunta,
      prova: questao.prova
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questao = this.createFromForm();
    if (questao.id !== undefined) {
      this.subscribeToSaveResponse(this.questaoService.update(questao));
    } else {
      this.subscribeToSaveResponse(this.questaoService.create(questao));
    }
  }

  private createFromForm(): IQuestao {
    return {
      ...new Questao(),
      id: this.editForm.get(['id'])!.value,
      ordem: this.editForm.get(['ordem'])!.value,
      enunciado: this.editForm.get(['enunciado'])!.value,
      pergunta: this.editForm.get(['pergunta'])!.value,
      prova: this.editForm.get(['prova'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestao>>): void {
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
