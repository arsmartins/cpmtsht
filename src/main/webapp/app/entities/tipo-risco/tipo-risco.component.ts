import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoRisco } from 'app/shared/model/tipo-risco.model';
import { AccountService } from 'app/core';
import { TipoRiscoService } from './tipo-risco.service';

@Component({
    selector: 'jhi-tipo-risco',
    templateUrl: './tipo-risco.component.html'
})
export class TipoRiscoComponent implements OnInit, OnDestroy {
    tipoRiscos: ITipoRisco[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected tipoRiscoService: TipoRiscoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.tipoRiscoService
            .query()
            .pipe(
                filter((res: HttpResponse<ITipoRisco[]>) => res.ok),
                map((res: HttpResponse<ITipoRisco[]>) => res.body)
            )
            .subscribe(
                (res: ITipoRisco[]) => {
                    this.tipoRiscos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTipoRiscos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITipoRisco) {
        return item.id;
    }

    registerChangeInTipoRiscos() {
        this.eventSubscriber = this.eventManager.subscribe('tipoRiscoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
