import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestao, Questao } from 'app/shared/model/questao.model';
import { QuestaoService } from './questao.service';
import { QuestaoComponent } from './questao.component';
import { QuestaoDetailComponent } from './questao-detail.component';
import { QuestaoUpdateComponent } from './questao-update.component';

@Injectable({ providedIn: 'root' })
export class QuestaoResolve implements Resolve<IQuestao> {
  constructor(private service: QuestaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questao: HttpResponse<Questao>) => {
          if (questao.body) {
            return of(questao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Questao());
  }
}

export const questaoRoute: Routes = [
  {
    path: '',
    component: QuestaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.questao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: QuestaoDetailComponent,
    resolve: {
      questao: QuestaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.questao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: QuestaoUpdateComponent,
    resolve: {
      questao: QuestaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.questao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: QuestaoUpdateComponent,
    resolve: {
      questao: QuestaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.questao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
