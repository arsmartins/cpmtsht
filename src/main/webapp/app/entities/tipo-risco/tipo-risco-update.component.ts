import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITipoRisco } from 'app/shared/model/tipo-risco.model';
import { TipoRiscoService } from './tipo-risco.service';

@Component({
    selector: 'jhi-tipo-risco-update',
    templateUrl: './tipo-risco-update.component.html'
})
export class TipoRiscoUpdateComponent implements OnInit {
    tipoRisco: ITipoRisco;
    isSaving: boolean;

    constructor(protected tipoRiscoService: TipoRiscoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoRisco }) => {
            this.tipoRisco = tipoRisco;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoRisco.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoRiscoService.update(this.tipoRisco));
        } else {
            this.subscribeToSaveResponse(this.tipoRiscoService.create(this.tipoRisco));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoRisco>>) {
        result.subscribe((res: HttpResponse<ITipoRisco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
