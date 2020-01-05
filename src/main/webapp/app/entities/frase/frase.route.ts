import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFrase, Frase } from 'app/shared/model/frase.model';
import { FraseService } from './frase.service';
import { FraseComponent } from './frase.component';
import { FraseDetailComponent } from './frase-detail.component';
import { FraseUpdateComponent } from './frase-update.component';

@Injectable({ providedIn: 'root' })
export class FraseResolve implements Resolve<IFrase> {
  constructor(private service: FraseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFrase> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((frase: HttpResponse<Frase>) => {
          if (frase.body) {
            return of(frase.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Frase());
  }
}

export const fraseRoute: Routes = [
  {
    path: '',
    component: FraseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.frase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FraseDetailComponent,
    resolve: {
      frase: FraseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.frase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FraseUpdateComponent,
    resolve: {
      frase: FraseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.frase.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FraseUpdateComponent,
    resolve: {
      frase: FraseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.frase.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
