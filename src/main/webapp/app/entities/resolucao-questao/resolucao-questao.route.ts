import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResolucaoQuestao, ResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';
import { ResolucaoQuestaoService } from './resolucao-questao.service';
import { ResolucaoQuestaoComponent } from './resolucao-questao.component';
import { ResolucaoQuestaoDetailComponent } from './resolucao-questao-detail.component';
import { ResolucaoQuestaoUpdateComponent } from './resolucao-questao-update.component';

@Injectable({ providedIn: 'root' })
export class ResolucaoQuestaoResolve implements Resolve<IResolucaoQuestao> {
  constructor(private service: ResolucaoQuestaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResolucaoQuestao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resolucaoQuestao: HttpResponse<ResolucaoQuestao>) => {
          if (resolucaoQuestao.body) {
            return of(resolucaoQuestao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResolucaoQuestao());
  }
}

export const resolucaoQuestaoRoute: Routes = [
  {
    path: '',
    component: ResolucaoQuestaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoQuestao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResolucaoQuestaoDetailComponent,
    resolve: {
      resolucaoQuestao: ResolucaoQuestaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoQuestao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResolucaoQuestaoUpdateComponent,
    resolve: {
      resolucaoQuestao: ResolucaoQuestaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoQuestao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResolucaoQuestaoUpdateComponent,
    resolve: {
      resolucaoQuestao: ResolucaoQuestaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoQuestao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
