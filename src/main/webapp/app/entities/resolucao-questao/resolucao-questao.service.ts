import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';

type EntityResponseType = HttpResponse<IResolucaoQuestao>;
type EntityArrayResponseType = HttpResponse<IResolucaoQuestao[]>;

@Injectable({ providedIn: 'root' })
export class ResolucaoQuestaoService {
  public resourceUrl = SERVER_API_URL + 'api/resolucao-questaos';

  constructor(protected http: HttpClient) {}

  create(resolucaoQuestao: IResolucaoQuestao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resolucaoQuestao);
    return this.http
      .post<IResolucaoQuestao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resolucaoQuestao: IResolucaoQuestao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resolucaoQuestao);
    return this.http
      .put<IResolucaoQuestao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResolucaoQuestao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResolucaoQuestao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(resolucaoQuestao: IResolucaoQuestao): IResolucaoQuestao {
    const copy: IResolucaoQuestao = Object.assign({}, resolucaoQuestao, {
      dataHoraInicio:
        resolucaoQuestao.dataHoraInicio && resolucaoQuestao.dataHoraInicio.isValid() ? resolucaoQuestao.dataHoraInicio.toJSON() : undefined,
      dataHoraFim:
        resolucaoQuestao.dataHoraFim && resolucaoQuestao.dataHoraFim.isValid() ? resolucaoQuestao.dataHoraFim.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataHoraInicio = res.body.dataHoraInicio ? moment(res.body.dataHoraInicio) : undefined;
      res.body.dataHoraFim = res.body.dataHoraFim ? moment(res.body.dataHoraFim) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((resolucaoQuestao: IResolucaoQuestao) => {
        resolucaoQuestao.dataHoraInicio = resolucaoQuestao.dataHoraInicio ? moment(resolucaoQuestao.dataHoraInicio) : undefined;
        resolucaoQuestao.dataHoraFim = resolucaoQuestao.dataHoraFim ? moment(resolucaoQuestao.dataHoraFim) : undefined;
      });
    }
    return res;
  }
}
