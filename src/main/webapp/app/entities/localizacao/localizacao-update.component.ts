import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from './localizacao.service';
import { ICoordenadas } from 'app/shared/model/coordenadas.model';
import { CoordenadasService } from 'app/entities/coordenadas';

@Component({
    selector: 'jhi-localizacao-update',
    templateUrl: './localizacao-update.component.html'
})
export class LocalizacaoUpdateComponent implements OnInit {
    localizacao: ILocalizacao;
    isSaving: boolean;

    coordenadas: ICoordenadas[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected localizacaoService: LocalizacaoService,
        protected coordenadasService: CoordenadasService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ localizacao }) => {
            this.localizacao = localizacao;
        });
        this.coordenadasService
            .query({ filter: 'localizacao-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICoordenadas[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICoordenadas[]>) => response.body)
            )
            .subscribe(
                (res: ICoordenadas[]) => {
                    if (!this.localizacao.coordenadas || !this.localizacao.coordenadas.id) {
                        this.coordenadas = res;
                    } else {
                        this.coordenadasService
                            .find(this.localizacao.coordenadas.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICoordenadas>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICoordenadas>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICoordenadas) => (this.coordenadas = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.localizacao.id !== undefined) {
            this.subscribeToSaveResponse(this.localizacaoService.update(this.localizacao));
        } else {
            this.subscribeToSaveResponse(this.localizacaoService.create(this.localizacao));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalizacao>>) {
        result.subscribe((res: HttpResponse<ILocalizacao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCoordenadasById(index: number, item: ICoordenadas) {
        return item.id;
    }
}
