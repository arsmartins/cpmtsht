import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';
import { PostoTrabalhoService } from './posto-trabalho.service';
import { IFuncionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario';

@Component({
    selector: 'jhi-posto-trabalho-update',
    templateUrl: './posto-trabalho-update.component.html'
})
export class PostoTrabalhoUpdateComponent implements OnInit {
    postoTrabalho: IPostoTrabalho;
    isSaving: boolean;

    funcionarios: IFuncionario[];
    dataInicio: string;
    dataFim: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected postoTrabalhoService: PostoTrabalhoService,
        protected funcionarioService: FuncionarioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ postoTrabalho }) => {
            this.postoTrabalho = postoTrabalho;
            this.dataInicio = this.postoTrabalho.dataInicio != null ? this.postoTrabalho.dataInicio.format(DATE_TIME_FORMAT) : null;
            this.dataFim = this.postoTrabalho.dataFim != null ? this.postoTrabalho.dataFim.format(DATE_TIME_FORMAT) : null;
        });
        this.funcionarioService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFuncionario[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFuncionario[]>) => response.body)
            )
            .subscribe((res: IFuncionario[]) => (this.funcionarios = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.postoTrabalho.dataInicio = this.dataInicio != null ? moment(this.dataInicio, DATE_TIME_FORMAT) : null;
        this.postoTrabalho.dataFim = this.dataFim != null ? moment(this.dataFim, DATE_TIME_FORMAT) : null;
        if (this.postoTrabalho.id !== undefined) {
            this.subscribeToSaveResponse(this.postoTrabalhoService.update(this.postoTrabalho));
        } else {
            this.subscribeToSaveResponse(this.postoTrabalhoService.create(this.postoTrabalho));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostoTrabalho>>) {
        result.subscribe((res: HttpResponse<IPostoTrabalho>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFuncionarioById(index: number, item: IFuncionario) {
        return item.id;
    }
}
