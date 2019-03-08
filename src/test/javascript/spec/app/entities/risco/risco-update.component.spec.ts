/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpmtshtTestModule } from '../../../test.module';
import { RiscoUpdateComponent } from 'app/entities/risco/risco-update.component';
import { RiscoService } from 'app/entities/risco/risco.service';
import { Risco } from 'app/shared/model/risco.model';

describe('Component Tests', () => {
    describe('Risco Management Update Component', () => {
        let comp: RiscoUpdateComponent;
        let fixture: ComponentFixture<RiscoUpdateComponent>;
        let service: RiscoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [RiscoUpdateComponent]
            })
                .overrideTemplate(RiscoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RiscoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiscoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Risco(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.risco = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Risco();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.risco = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
