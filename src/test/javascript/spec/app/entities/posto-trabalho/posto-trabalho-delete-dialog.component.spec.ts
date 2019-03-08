/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpmtshtTestModule } from '../../../test.module';
import { PostoTrabalhoDeleteDialogComponent } from 'app/entities/posto-trabalho/posto-trabalho-delete-dialog.component';
import { PostoTrabalhoService } from 'app/entities/posto-trabalho/posto-trabalho.service';

describe('Component Tests', () => {
    describe('PostoTrabalho Management Delete Component', () => {
        let comp: PostoTrabalhoDeleteDialogComponent;
        let fixture: ComponentFixture<PostoTrabalhoDeleteDialogComponent>;
        let service: PostoTrabalhoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [PostoTrabalhoDeleteDialogComponent]
            })
                .overrideTemplate(PostoTrabalhoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PostoTrabalhoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PostoTrabalhoService);
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
