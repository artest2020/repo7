import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEdital } from 'app/shared/model/edital.model';

type EntityResponseType = HttpResponse<IEdital>;
type EntityArrayResponseType = HttpResponse<IEdital[]>;

@Injectable({ providedIn: 'root' })
export class EditalService {
  public resourceUrl = SERVER_API_URL + 'api/editals';

  constructor(protected http: HttpClient) {}

  create(edital: IEdital): Observable<EntityResponseType> {
    return this.http.post<IEdital>(this.resourceUrl, edital, { observe: 'response' });
  }

  update(edital: IEdital): Observable<EntityResponseType> {
    return this.http.put<IEdital>(this.resourceUrl, edital, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEdital>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEdital[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
