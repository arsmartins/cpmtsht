import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from './estabelecimento.service';
import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from 'app/entities/localizacao';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
    selector: 'jhi-estabelecimento-update',
    templateUrl: './estabelecimento-update.component.html'
})
export class EstabelecimentoUpdateComponent implements OnInit {
    estabelecimento: IEstabelecimento;
    isSaving: boolean;

    localizacaos: ILocalizacao[];

    empresas: IEmpresa[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected estabelecimentoService: EstabelecimentoService,
        protected localizacaoService: LocalizacaoService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estabelecimento }) => {
            this.estabelecimento = estabelecimento;
        });
        this.localizacaoService
            .query({ filter: 'estabelecimento-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ILocalizacao[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILocalizacao[]>) => response.body)
            )
            .subscribe(
                (res: ILocalizacao[]) => {
                    if (!this.estabelecimento.localizacao || !this.estabelecimento.localizacao.id) {
                        this.localizacaos = res;
                    } else {
                        this.localizacaoService
                            .find(this.estabelecimento.localizacao.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ILocalizacao>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ILocalizacao>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ILocalizacao) => (this.localizacaos = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.empresaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmpresa[]>) => response.body)
            )
            .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estabelecimento.id !== undefined) {
            this.subscribeToSaveResponse(this.estabelecimentoService.update(this.estabelecimento));
        } else {
            this.subscribeToSaveResponse(this.estabelecimentoService.create(this.estabelecimento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstabelecimento>>) {
        result.subscribe((res: HttpResponse<IEstabelecimento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLocalizacaoById(index: number, item: ILocalizacao) {
        return item.id;
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }
}
