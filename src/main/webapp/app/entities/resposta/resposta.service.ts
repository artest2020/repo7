import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IResposta } from 'app/shared/model/resposta.model';

type EntityResponseType = HttpResponse<IResposta>;
type EntityArrayResponseType = HttpResponse<IResposta[]>;

@Injectable({ providedIn: 'root' })
export class RespostaService {
  public resourceUrl = SERVER_API_URL + 'api/respostas';

  constructor(protected http: HttpClient) {}

  create(resposta: IResposta): Observable<EntityResponseType> {
    return this.http.post<IResposta>(this.resourceUrl, resposta, { observe: 'response' });
  }

  update(resposta: IResposta): Observable<EntityResponseType> {
    return this.http.put<IResposta>(this.resourceUrl, resposta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResposta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResposta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
