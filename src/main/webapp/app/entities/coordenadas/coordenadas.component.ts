import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICoordenadas } from 'app/shared/model/coordenadas.model';
import { AccountService } from 'app/core';
import { CoordenadasService } from './coordenadas.service';

@Component({
    selector: 'jhi-coordenadas',
    templateUrl: './coordenadas.component.html'
})
export class CoordenadasComponent implements OnInit, OnDestroy {
    coordenadas: ICoordenadas[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected coordenadasService: CoordenadasService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.coordenadasService
            .query()
            .pipe(
                filter((res: HttpResponse<ICoordenadas[]>) => res.ok),
                map((res: HttpResponse<ICoordenadas[]>) => res.body)
            )
            .subscribe(
                (res: ICoordenadas[]) => {
                    this.coordenadas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCoordenadas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICoordenadas) {
        return item.id;
    }

    registerChangeInCoordenadas() {
        this.eventSubscriber = this.eventManager.subscribe('coordenadasListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
