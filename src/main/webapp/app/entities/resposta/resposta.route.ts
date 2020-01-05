import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResposta, Resposta } from 'app/shared/model/resposta.model';
import { RespostaService } from './resposta.service';
import { RespostaComponent } from './resposta.component';
import { RespostaDetailComponent } from './resposta-detail.component';
import { RespostaUpdateComponent } from './resposta-update.component';

@Injectable({ providedIn: 'root' })
export class RespostaResolve implements Resolve<IResposta> {
  constructor(private service: RespostaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResposta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resposta: HttpResponse<Resposta>) => {
          if (resposta.body) {
            return of(resposta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Resposta());
  }
}

export const respostaRoute: Routes = [
  {
    path: '',
    component: RespostaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resposta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RespostaDetailComponent,
    resolve: {
      resposta: RespostaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resposta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RespostaUpdateComponent,
    resolve: {
      resposta: RespostaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resposta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RespostaUpdateComponent,
    resolve: {
      resposta: RespostaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resposta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
