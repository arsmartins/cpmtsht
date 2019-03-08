import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoRisco } from 'app/shared/model/tipo-risco.model';

@Component({
    selector: 'jhi-tipo-risco-detail',
    templateUrl: './tipo-risco-detail.component.html'
})
export class TipoRiscoDetailComponent implements OnInit {
    tipoRisco: ITipoRisco;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoRisco }) => {
            this.tipoRisco = tipoRisco;
        });
    }

    previousState() {
        window.history.back();
    }
}
