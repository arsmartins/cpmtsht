import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IFuncionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from './funcionario.service';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';

@Component({
    selector: 'jhi-funcionario-update',
    templateUrl: './funcionario-update.component.html'
})
export class FuncionarioUpdateComponent implements OnInit {
    funcionario: IFuncionario;
    isSaving: boolean;

    estabelecimentos: IEstabelecimento[];

    empresas: IEmpresa[];
    dataEntrada: string;
    dataSaida: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected funcionarioService: FuncionarioService,
        protected estabelecimentoService: EstabelecimentoService,
        protected empresaService: EmpresaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ funcionario }) => {
            this.funcionario = funcionario;
            this.dataEntrada = this.funcionario.dataEntrada != null ? this.funcionario.dataEntrada.format(DATE_TIME_FORMAT) : null;
            this.dataSaida = this.funcionario.dataSaida != null ? this.funcionario.dataSaida.format(DATE_TIME_FORMAT) : null;
        });
        this.estabelecimentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstabelecimento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstabelecimento[]>) => response.body)
            )
            .subscribe((res: IEstabelecimento[]) => (this.estabelecimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.funcionario.dataEntrada = this.dataEntrada != null ? moment(this.dataEntrada, DATE_TIME_FORMAT) : null;
        this.funcionario.dataSaida = this.dataSaida != null ? moment(this.dataSaida, DATE_TIME_FORMAT) : null;
        if (this.funcionario.id !== undefined) {
            this.subscribeToSaveResponse(this.funcionarioService.update(this.funcionario));
        } else {
            this.subscribeToSaveResponse(this.funcionarioService.create(this.funcionario));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionario>>) {
        result.subscribe((res: HttpResponse<IFuncionario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEstabelecimentoById(index: number, item: IEstabelecimento) {
        return item.id;
    }

    trackEmpresaById(index: number, item: IEmpresa) {
        return item.id;
    }
}
