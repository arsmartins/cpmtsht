import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { AccountService } from 'app/core';
import { LocalizacaoService } from './localizacao.service';

@Component({
    selector: 'jhi-localizacao',
    templateUrl: './localizacao.component.html'
})
export class LocalizacaoComponent implements OnInit, OnDestroy {
    localizacaos: ILocalizacao[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected localizacaoService: LocalizacaoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.localizacaoService
            .query()
            .pipe(
                filter((res: HttpResponse<ILocalizacao[]>) => res.ok),
                map((res: HttpResponse<ILocalizacao[]>) => res.body)
            )
            .subscribe(
                (res: ILocalizacao[]) => {
                    this.localizacaos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLocalizacaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILocalizacao) {
        return item.id;
    }

    registerChangeInLocalizacaos() {
        this.eventSubscriber = this.eventManager.subscribe('localizacaoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
