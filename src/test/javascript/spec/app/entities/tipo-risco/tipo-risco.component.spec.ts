/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpmtshtTestModule } from '../../../test.module';
import { TipoRiscoComponent } from 'app/entities/tipo-risco/tipo-risco.component';
import { TipoRiscoService } from 'app/entities/tipo-risco/tipo-risco.service';
import { TipoRisco } from 'app/shared/model/tipo-risco.model';

describe('Component Tests', () => {
    describe('TipoRisco Management Component', () => {
        let comp: TipoRiscoComponent;
        let fixture: ComponentFixture<TipoRiscoComponent>;
        let service: TipoRiscoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [TipoRiscoComponent],
                providers: []
            })
                .overrideTemplate(TipoRiscoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoRiscoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoRiscoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TipoRisco(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tipoRiscos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
