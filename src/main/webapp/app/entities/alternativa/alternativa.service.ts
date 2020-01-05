import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlternativa } from 'app/shared/model/alternativa.model';

type EntityResponseType = HttpResponse<IAlternativa>;
type EntityArrayResponseType = HttpResponse<IAlternativa[]>;

@Injectable({ providedIn: 'root' })
export class AlternativaService {
  public resourceUrl = SERVER_API_URL + 'api/alternativas';

  constructor(protected http: HttpClient) {}

  create(alternativa: IAlternativa): Observable<EntityResponseType> {
    return this.http.post<IAlternativa>(this.resourceUrl, alternativa, { observe: 'response' });
  }

  update(alternativa: IAlternativa): Observable<EntityResponseType> {
    return this.http.put<IAlternativa>(this.resourceUrl, alternativa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlternativa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlternativa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
