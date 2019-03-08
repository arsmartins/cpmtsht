import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';
import { PostoTrabalhoService } from './posto-trabalho.service';

@Component({
    selector: 'jhi-posto-trabalho-delete-dialog',
    templateUrl: './posto-trabalho-delete-dialog.component.html'
})
export class PostoTrabalhoDeleteDialogComponent {
    postoTrabalho: IPostoTrabalho;

    constructor(
        protected postoTrabalhoService: PostoTrabalhoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.postoTrabalhoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'postoTrabalhoListModification',
                content: 'Deleted an postoTrabalho'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-posto-trabalho-delete-popup',
    template: ''
})
export class PostoTrabalhoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ postoTrabalho }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PostoTrabalhoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.postoTrabalho = postoTrabalho;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/posto-trabalho', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/posto-trabalho', { outlets: { popup: null } }]);
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
