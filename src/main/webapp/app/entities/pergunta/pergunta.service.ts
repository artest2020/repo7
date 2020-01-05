import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPergunta } from 'app/shared/model/pergunta.model';

type EntityResponseType = HttpResponse<IPergunta>;
type EntityArrayResponseType = HttpResponse<IPergunta[]>;

@Injectable({ providedIn: 'root' })
export class PerguntaService {
  public resourceUrl = SERVER_API_URL + 'api/perguntas';

  constructor(protected http: HttpClient) {}

  create(pergunta: IPergunta): Observable<EntityResponseType> {
    return this.http.post<IPergunta>(this.resourceUrl, pergunta, { observe: 'response' });
  }

  update(pergunta: IPergunta): Observable<EntityResponseType> {
    return this.http.put<IPergunta>(this.resourceUrl, pergunta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPergunta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPergunta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
