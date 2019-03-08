import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';

@Component({
    selector: 'jhi-posto-trabalho-detail',
    templateUrl: './posto-trabalho-detail.component.html'
})
export class PostoTrabalhoDetailComponent implements OnInit {
    postoTrabalho: IPostoTrabalho;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ postoTrabalho }) => {
            this.postoTrabalho = postoTrabalho;
        });
    }

    previousState() {
        window.history.back();
    }
}
