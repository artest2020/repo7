import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInstituicao, Instituicao } from 'app/shared/model/instituicao.model';
import { InstituicaoService } from './instituicao.service';
import { InstituicaoComponent } from './instituicao.component';
import { InstituicaoDetailComponent } from './instituicao-detail.component';
import { InstituicaoUpdateComponent } from './instituicao-update.component';

@Injectable({ providedIn: 'root' })
export class InstituicaoResolve implements Resolve<IInstituicao> {
  constructor(private service: InstituicaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInstituicao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((instituicao: HttpResponse<Instituicao>) => {
          if (instituicao.body) {
            return of(instituicao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Instituicao());
  }
}

export const instituicaoRoute: Routes = [
  {
    path: '',
    component: InstituicaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.instituicao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InstituicaoDetailComponent,
    resolve: {
      instituicao: InstituicaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.instituicao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InstituicaoUpdateComponent,
    resolve: {
      instituicao: InstituicaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.instituicao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InstituicaoUpdateComponent,
    resolve: {
      instituicao: InstituicaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.instituicao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
