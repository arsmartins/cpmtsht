import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRisco } from 'app/shared/model/risco.model';
import { AccountService } from 'app/core';
import { RiscoService } from './risco.service';

@Component({
    selector: 'jhi-risco',
    templateUrl: './risco.component.html'
})
export class RiscoComponent implements OnInit, OnDestroy {
    riscos: IRisco[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected riscoService: RiscoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.riscoService
            .query()
            .pipe(
                filter((res: HttpResponse<IRisco[]>) => res.ok),
                map((res: HttpResponse<IRisco[]>) => res.body)
            )
            .subscribe(
                (res: IRisco[]) => {
                    this.riscos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRiscos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRisco) {
        return item.id;
    }

    registerChangeInRiscos() {
        this.eventSubscriber = this.eventManager.subscribe('riscoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
