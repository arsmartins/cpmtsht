import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRisco } from 'app/shared/model/risco.model';
import { RiscoService } from './risco.service';
import { ITipoRisco } from 'app/shared/model/tipo-risco.model';
import { TipoRiscoService } from 'app/entities/tipo-risco';
import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';
import { PostoTrabalhoService } from 'app/entities/posto-trabalho';

@Component({
    selector: 'jhi-risco-update',
    templateUrl: './risco-update.component.html'
})
export class RiscoUpdateComponent implements OnInit {
    risco: IRisco;
    isSaving: boolean;

    tipos: ITipoRisco[];

    postotrabalhos: IPostoTrabalho[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected riscoService: RiscoService,
        protected tipoRiscoService: TipoRiscoService,
        protected postoTrabalhoService: PostoTrabalhoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ risco }) => {
            this.risco = risco;
        });
        this.tipoRiscoService
            .query({ filter: 'risco-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ITipoRisco[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITipoRisco[]>) => response.body)
            )
            .subscribe(
                (res: ITipoRisco[]) => {
                    if (!this.risco.tipo || !this.risco.tipo.id) {
                        this.tipos = res;
                    } else {
                        this.tipoRiscoService
                            .find(this.risco.tipo.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ITipoRisco>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ITipoRisco>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ITipoRisco) => (this.tipos = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.postoTrabalhoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPostoTrabalho[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPostoTrabalho[]>) => response.body)
            )
            .subscribe((res: IPostoTrabalho[]) => (this.postotrabalhos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.risco.id !== undefined) {
            this.subscribeToSaveResponse(this.riscoService.update(this.risco));
        } else {
            this.subscribeToSaveResponse(this.riscoService.create(this.risco));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRisco>>) {
        result.subscribe((res: HttpResponse<IRisco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoRiscoById(index: number, item: ITipoRisco) {
        return item.id;
    }

    trackPostoTrabalhoById(index: number, item: IPostoTrabalho) {
        return item.id;
    }
}
