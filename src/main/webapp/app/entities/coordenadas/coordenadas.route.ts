import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Coordenadas } from 'app/shared/model/coordenadas.model';
import { CoordenadasService } from './coordenadas.service';
import { CoordenadasComponent } from './coordenadas.component';
import { CoordenadasDetailComponent } from './coordenadas-detail.component';
import { CoordenadasUpdateComponent } from './coordenadas-update.component';
import { CoordenadasDeletePopupComponent } from './coordenadas-delete-dialog.component';
import { ICoordenadas } from 'app/shared/model/coordenadas.model';

@Injectable({ providedIn: 'root' })
export class CoordenadasResolve implements Resolve<ICoordenadas> {
    constructor(private service: CoordenadasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICoordenadas> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Coordenadas>) => response.ok),
                map((coordenadas: HttpResponse<Coordenadas>) => coordenadas.body)
            );
        }
        return of(new Coordenadas());
    }
}

export const coordenadasRoute: Routes = [
    {
        path: '',
        component: CoordenadasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.coordenadas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CoordenadasDetailComponent,
        resolve: {
            coordenadas: CoordenadasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.coordenadas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CoordenadasUpdateComponent,
        resolve: {
            coordenadas: CoordenadasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.coordenadas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CoordenadasUpdateComponent,
        resolve: {
            coordenadas: CoordenadasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.coordenadas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coordenadasPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CoordenadasDeletePopupComponent,
        resolve: {
            coordenadas: CoordenadasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.coordenadas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
