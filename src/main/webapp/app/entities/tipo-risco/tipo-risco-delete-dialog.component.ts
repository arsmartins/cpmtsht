import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoRisco } from 'app/shared/model/tipo-risco.model';
import { TipoRiscoService } from './tipo-risco.service';

@Component({
    selector: 'jhi-tipo-risco-delete-dialog',
    templateUrl: './tipo-risco-delete-dialog.component.html'
})
export class TipoRiscoDeleteDialogComponent {
    tipoRisco: ITipoRisco;

    constructor(
        protected tipoRiscoService: TipoRiscoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoRiscoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoRiscoListModification',
                content: 'Deleted an tipoRisco'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-risco-delete-popup',
    template: ''
})
export class TipoRiscoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoRisco }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoRiscoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tipoRisco = tipoRisco;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/tipo-risco', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/tipo-risco', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
