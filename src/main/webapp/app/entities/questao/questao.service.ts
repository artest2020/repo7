import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestao } from 'app/shared/model/questao.model';

type EntityResponseType = HttpResponse<IQuestao>;
type EntityArrayResponseType = HttpResponse<IQuestao[]>;

@Injectable({ providedIn: 'root' })
export class QuestaoService {
  public resourceUrl = SERVER_API_URL + 'api/questaos';

  constructor(protected http: HttpClient) {}

  create(questao: IQuestao): Observable<EntityResponseType> {
    return this.http.post<IQuestao>(this.resourceUrl, questao, { observe: 'response' });
  }

  update(questao: IQuestao): Observable<EntityResponseType> {
    return this.http.put<IQuestao>(this.resourceUrl, questao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
