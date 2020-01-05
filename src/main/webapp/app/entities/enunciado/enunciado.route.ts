import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnunciado, Enunciado } from 'app/shared/model/enunciado.model';
import { EnunciadoService } from './enunciado.service';
import { EnunciadoComponent } from './enunciado.component';
import { EnunciadoDetailComponent } from './enunciado-detail.component';
import { EnunciadoUpdateComponent } from './enunciado-update.component';

@Injectable({ providedIn: 'root' })
export class EnunciadoResolve implements Resolve<IEnunciado> {
  constructor(private service: EnunciadoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnunciado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enunciado: HttpResponse<Enunciado>) => {
          if (enunciado.body) {
            return of(enunciado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Enunciado());
  }
}

export const enunciadoRoute: Routes = [
  {
    path: '',
    component: EnunciadoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.enunciado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnunciadoDetailComponent,
    resolve: {
      enunciado: EnunciadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.enunciado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnunciadoUpdateComponent,
    resolve: {
      enunciado: EnunciadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.enunciado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnunciadoUpdateComponent,
    resolve: {
      enunciado: EnunciadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.enunciado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
