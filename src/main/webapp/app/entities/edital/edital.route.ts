import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEdital, Edital } from 'app/shared/model/edital.model';
import { EditalService } from './edital.service';
import { EditalComponent } from './edital.component';
import { EditalDetailComponent } from './edital-detail.component';
import { EditalUpdateComponent } from './edital-update.component';

@Injectable({ providedIn: 'root' })
export class EditalResolve implements Resolve<IEdital> {
  constructor(private service: EditalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEdital> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((edital: HttpResponse<Edital>) => {
          if (edital.body) {
            return of(edital.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Edital());
  }
}

export const editalRoute: Routes = [
  {
    path: '',
    component: EditalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.edital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EditalDetailComponent,
    resolve: {
      edital: EditalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.edital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EditalUpdateComponent,
    resolve: {
      edital: EditalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.edital.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EditalUpdateComponent,
    resolve: {
      edital: EditalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.edital.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
