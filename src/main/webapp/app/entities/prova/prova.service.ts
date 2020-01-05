import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProva } from 'app/shared/model/prova.model';

type EntityResponseType = HttpResponse<IProva>;
type EntityArrayResponseType = HttpResponse<IProva[]>;

@Injectable({ providedIn: 'root' })
export class ProvaService {
  public resourceUrl = SERVER_API_URL + 'api/prova';

  constructor(protected http: HttpClient) {}

  create(prova: IProva): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prova);
    return this.http
      .post<IProva>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(prova: IProva): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prova);
    return this.http
      .put<IProva>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProva>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProva[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(prova: IProva): IProva {
    const copy: IProva = Object.assign({}, prova, {
      dataHoraInicio: prova.dataHoraInicio && prova.dataHoraInicio.isValid() ? prova.dataHoraInicio.toJSON() : undefined,
      dataHoraFim: prova.dataHoraFim && prova.dataHoraFim.isValid() ? prova.dataHoraFim.toJSON() : undefined
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
      res.body.forEach((prova: IProva) => {
        prova.dataHoraInicio = prova.dataHoraInicio ? moment(prova.dataHoraInicio) : undefined;
        prova.dataHoraFim = prova.dataHoraFim ? moment(prova.dataHoraFim) : undefined;
      });
    }
    return res;
  }
}
