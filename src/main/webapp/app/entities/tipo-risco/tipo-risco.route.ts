import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoRisco } from 'app/shared/model/tipo-risco.model';
import { TipoRiscoService } from './tipo-risco.service';
import { TipoRiscoComponent } from './tipo-risco.component';
import { TipoRiscoDetailComponent } from './tipo-risco-detail.component';
import { TipoRiscoUpdateComponent } from './tipo-risco-update.component';
import { TipoRiscoDeletePopupComponent } from './tipo-risco-delete-dialog.component';
import { ITipoRisco } from 'app/shared/model/tipo-risco.model';

@Injectable({ providedIn: 'root' })
export class TipoRiscoResolve implements Resolve<ITipoRisco> {
    constructor(private service: TipoRiscoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoRisco> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TipoRisco>) => response.ok),
                map((tipoRisco: HttpResponse<TipoRisco>) => tipoRisco.body)
            );
        }
        return of(new TipoRisco());
    }
}

export const tipoRiscoRoute: Routes = [
    {
        path: '',
        component: TipoRiscoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.tipoRisco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TipoRiscoDetailComponent,
        resolve: {
            tipoRisco: TipoRiscoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.tipoRisco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TipoRiscoUpdateComponent,
        resolve: {
            tipoRisco: TipoRiscoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.tipoRisco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TipoRiscoUpdateComponent,
        resolve: {
            tipoRisco: TipoRiscoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.tipoRisco.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoRiscoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TipoRiscoDeletePopupComponent,
        resolve: {
            tipoRisco: TipoRiscoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.tipoRisco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
