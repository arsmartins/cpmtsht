import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { AccountService } from 'app/core';
import { EstabelecimentoService } from './estabelecimento.service';

@Component({
    selector: 'jhi-estabelecimento',
    templateUrl: './estabelecimento.component.html'
})
export class EstabelecimentoComponent implements OnInit, OnDestroy {
    estabelecimentos: IEstabelecimento[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected estabelecimentoService: EstabelecimentoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.estabelecimentoService
            .query()
            .pipe(
                filter((res: HttpResponse<IEstabelecimento[]>) => res.ok),
                map((res: HttpResponse<IEstabelecimento[]>) => res.body)
            )
            .subscribe(
                (res: IEstabelecimento[]) => {
                    this.estabelecimentos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEstabelecimentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEstabelecimento) {
        return item.id;
    }

    registerChangeInEstabelecimentos() {
        this.eventSubscriber = this.eventManager.subscribe('estabelecimentoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
