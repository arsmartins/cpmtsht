/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpmtshtTestModule } from '../../../test.module';
import { RiscoDeleteDialogComponent } from 'app/entities/risco/risco-delete-dialog.component';
import { RiscoService } from 'app/entities/risco/risco.service';

describe('Component Tests', () => {
    describe('Risco Management Delete Component', () => {
        let comp: RiscoDeleteDialogComponent;
        let fixture: ComponentFixture<RiscoDeleteDialogComponent>;
        let service: RiscoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [RiscoDeleteDialogComponent]
            })
                .overrideTemplate(RiscoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RiscoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiscoService);
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
