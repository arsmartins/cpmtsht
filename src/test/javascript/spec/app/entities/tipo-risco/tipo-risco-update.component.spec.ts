/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpmtshtTestModule } from '../../../test.module';
import { TipoRiscoUpdateComponent } from 'app/entities/tipo-risco/tipo-risco-update.component';
import { TipoRiscoService } from 'app/entities/tipo-risco/tipo-risco.service';
import { TipoRisco } from 'app/shared/model/tipo-risco.model';

describe('Component Tests', () => {
    describe('TipoRisco Management Update Component', () => {
        let comp: TipoRiscoUpdateComponent;
        let fixture: ComponentFixture<TipoRiscoUpdateComponent>;
        let service: TipoRiscoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [TipoRiscoUpdateComponent]
            })
                .overrideTemplate(TipoRiscoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoRiscoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoRiscoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoRisco(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoRisco = entity;
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
                    const entity = new TipoRisco();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoRisco = entity;
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
