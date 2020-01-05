import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlternativa, Alternativa } from 'app/shared/model/alternativa.model';
import { AlternativaService } from './alternativa.service';
import { AlternativaComponent } from './alternativa.component';
import { AlternativaDetailComponent } from './alternativa-detail.component';
import { AlternativaUpdateComponent } from './alternativa-update.component';

@Injectable({ providedIn: 'root' })
export class AlternativaResolve implements Resolve<IAlternativa> {
  constructor(private service: AlternativaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlternativa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alternativa: HttpResponse<Alternativa>) => {
          if (alternativa.body) {
            return of(alternativa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alternativa());
  }
}

export const alternativaRoute: Routes = [
  {
    path: '',
    component: AlternativaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.alternativa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlternativaDetailComponent,
    resolve: {
      alternativa: AlternativaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.alternativa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlternativaUpdateComponent,
    resolve: {
      alternativa: AlternativaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.alternativa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlternativaUpdateComponent,
    resolve: {
      alternativa: AlternativaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.alternativa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
