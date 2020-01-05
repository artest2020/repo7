import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEnunciado } from 'app/shared/model/enunciado.model';

type EntityResponseType = HttpResponse<IEnunciado>;
type EntityArrayResponseType = HttpResponse<IEnunciado[]>;

@Injectable({ providedIn: 'root' })
export class EnunciadoService {
  public resourceUrl = SERVER_API_URL + 'api/enunciados';

  constructor(protected http: HttpClient) {}

  create(enunciado: IEnunciado): Observable<EntityResponseType> {
    return this.http.post<IEnunciado>(this.resourceUrl, enunciado, { observe: 'response' });
  }

  update(enunciado: IEnunciado): Observable<EntityResponseType> {
    return this.http.put<IEnunciado>(this.resourceUrl, enunciado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnunciado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnunciado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
