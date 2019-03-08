/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpmtshtTestModule } from '../../../test.module';
import { TipoRiscoDeleteDialogComponent } from 'app/entities/tipo-risco/tipo-risco-delete-dialog.component';
import { TipoRiscoService } from 'app/entities/tipo-risco/tipo-risco.service';

describe('Component Tests', () => {
    describe('TipoRisco Management Delete Component', () => {
        let comp: TipoRiscoDeleteDialogComponent;
        let fixture: ComponentFixture<TipoRiscoDeleteDialogComponent>;
        let service: TipoRiscoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [TipoRiscoDeleteDialogComponent]
            })
                .overrideTemplate(TipoRiscoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoRiscoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoRiscoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
