import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPergunta, Pergunta } from 'app/shared/model/pergunta.model';
import { PerguntaService } from './pergunta.service';
import { PerguntaComponent } from './pergunta.component';
import { PerguntaDetailComponent } from './pergunta-detail.component';
import { PerguntaUpdateComponent } from './pergunta-update.component';

@Injectable({ providedIn: 'root' })
export class PerguntaResolve implements Resolve<IPergunta> {
  constructor(private service: PerguntaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPergunta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pergunta: HttpResponse<Pergunta>) => {
          if (pergunta.body) {
            return of(pergunta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pergunta());
  }
}

export const perguntaRoute: Routes = [
  {
    path: '',
    component: PerguntaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.pergunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PerguntaDetailComponent,
    resolve: {
      pergunta: PerguntaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.pergunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PerguntaUpdateComponent,
    resolve: {
      pergunta: PerguntaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.pergunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PerguntaUpdateComponent,
    resolve: {
      pergunta: PerguntaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'repo7App.pergunta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
