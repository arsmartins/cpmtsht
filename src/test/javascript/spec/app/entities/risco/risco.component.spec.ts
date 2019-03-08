/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpmtshtTestModule } from '../../../test.module';
import { RiscoComponent } from 'app/entities/risco/risco.component';
import { RiscoService } from 'app/entities/risco/risco.service';
import { Risco } from 'app/shared/model/risco.model';

describe('Component Tests', () => {
    describe('Risco Management Component', () => {
        let comp: RiscoComponent;
        let fixture: ComponentFixture<RiscoComponent>;
        let service: RiscoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [RiscoComponent],
                providers: []
            })
                .overrideTemplate(RiscoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RiscoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RiscoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Risco(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.riscos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
