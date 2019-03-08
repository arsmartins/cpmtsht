import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { LocalizacaoService } from './localizacao.service';

@Component({
    selector: 'jhi-localizacao-delete-dialog',
    templateUrl: './localizacao-delete-dialog.component.html'
})
export class LocalizacaoDeleteDialogComponent {
    localizacao: ILocalizacao;

    constructor(
        protected localizacaoService: LocalizacaoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.localizacaoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'localizacaoListModification',
                content: 'Deleted an localizacao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-localizacao-delete-popup',
    template: ''
})
export class LocalizacaoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ localizacao }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LocalizacaoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.localizacao = localizacao;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/localizacao', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/localizacao', { outlets: { popup: null } }]);
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
