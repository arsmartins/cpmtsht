import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PostoTrabalho } from 'app/shared/model/posto-trabalho.model';
import { PostoTrabalhoService } from './posto-trabalho.service';
import { PostoTrabalhoComponent } from './posto-trabalho.component';
import { PostoTrabalhoDetailComponent } from './posto-trabalho-detail.component';
import { PostoTrabalhoUpdateComponent } from './posto-trabalho-update.component';
import { PostoTrabalhoDeletePopupComponent } from './posto-trabalho-delete-dialog.component';
import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';

@Injectable({ providedIn: 'root' })
export class PostoTrabalhoResolve implements Resolve<IPostoTrabalho> {
    constructor(private service: PostoTrabalhoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPostoTrabalho> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PostoTrabalho>) => response.ok),
                map((postoTrabalho: HttpResponse<PostoTrabalho>) => postoTrabalho.body)
            );
        }
        return of(new PostoTrabalho());
    }
}

export const postoTrabalhoRoute: Routes = [
    {
        path: '',
        component: PostoTrabalhoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cpmtshtApp.postoTrabalho.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PostoTrabalhoDetailComponent,
        resolve: {
            postoTrabalho: PostoTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.postoTrabalho.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PostoTrabalhoUpdateComponent,
        resolve: {
            postoTrabalho: PostoTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.postoTrabalho.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PostoTrabalhoUpdateComponent,
        resolve: {
            postoTrabalho: PostoTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.postoTrabalho.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const postoTrabalhoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PostoTrabalhoDeletePopupComponent,
        resolve: {
            postoTrabalho: PostoTrabalhoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cpmtshtApp.postoTrabalho.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
