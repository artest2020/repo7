import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResolucaoProva, ResolucaoProva } from 'app/shared/model/resolucao-prova.model';
import { ResolucaoProvaService } from './resolucao-prova.service';
import { ResolucaoProvaComponent } from './resolucao-prova.component';
import { ResolucaoProvaDetailComponent } from './resolucao-prova-detail.component';
import { ResolucaoProvaUpdateComponent } from './resolucao-prova-update.component';

@Injectable({ providedIn: 'root' })
export class ResolucaoProvaResolve implements Resolve<IResolucaoProva> {
  constructor(private service: ResolucaoProvaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResolucaoProva> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resolucaoProva: HttpResponse<ResolucaoProva>) => {
          if (resolucaoProva.body) {
            return of(resolucaoProva.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResolucaoProva());
  }
}

export const resolucaoProvaRoute: Routes = [
  {
    path: '',
    component: ResolucaoProvaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoProva.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResolucaoProvaDetailComponent,
    resolve: {
      resolucaoProva: ResolucaoProvaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoProva.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResolucaoProvaUpdateComponent,
    resolve: {
      resolucaoProva: ResolucaoProvaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoProva.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResolucaoProvaUpdateComponent,
    resolve: {
      resolucaoProva: ResolucaoProvaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.resolucaoProva.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
