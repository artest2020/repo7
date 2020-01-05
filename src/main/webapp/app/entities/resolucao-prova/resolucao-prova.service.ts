import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResolucaoProva } from 'app/shared/model/resolucao-prova.model';

type EntityResponseType = HttpResponse<IResolucaoProva>;
type EntityArrayResponseType = HttpResponse<IResolucaoProva[]>;

@Injectable({ providedIn: 'root' })
export class ResolucaoProvaService {
  public resourceUrl = SERVER_API_URL + 'api/resolucao-prova';

  constructor(protected http: HttpClient) {}

  create(resolucaoProva: IResolucaoProva): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resolucaoProva);
    return this.http
      .post<IResolucaoProva>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(resolucaoProva: IResolucaoProva): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(resolucaoProva);
    return this.http
      .put<IResolucaoProva>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResolucaoProva>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResolucaoProva[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(resolucaoProva: IResolucaoProva): IResolucaoProva {
    const copy: IResolucaoProva = Object.assign({}, resolucaoProva, {
      dataHoraInicio:
        resolucaoProva.dataHoraInicio && resolucaoProva.dataHoraInicio.isValid() ? resolucaoProva.dataHoraInicio.toJSON() : undefined,
      dataHoraFim: resolucaoProva.dataHoraFim && resolucaoProva.dataHoraFim.isValid() ? resolucaoProva.dataHoraFim.toJSON() : undefined
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
      res.body.forEach((resolucaoProva: IResolucaoProva) => {
        resolucaoProva.dataHoraInicio = resolucaoProva.dataHoraInicio ? moment(resolucaoProva.dataHoraInicio) : undefined;
        resolucaoProva.dataHoraFim = resolucaoProva.dataHoraFim ? moment(resolucaoProva.dataHoraFim) : undefined;
      });
    }
    return res;
  }
}
