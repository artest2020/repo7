import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFrase } from 'app/shared/model/frase.model';

type EntityResponseType = HttpResponse<IFrase>;
type EntityArrayResponseType = HttpResponse<IFrase[]>;

@Injectable({ providedIn: 'root' })
export class FraseService {
  public resourceUrl = SERVER_API_URL + 'api/frases';

  constructor(protected http: HttpClient) {}

  create(frase: IFrase): Observable<EntityResponseType> {
    return this.http.post<IFrase>(this.resourceUrl, frase, { observe: 'response' });
  }

  update(frase: IFrase): Observable<EntityResponseType> {
    return this.http.put<IFrase>(this.resourceUrl, frase, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFrase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFrase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
