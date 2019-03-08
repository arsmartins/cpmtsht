/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpmtshtTestModule } from '../../../test.module';
import { CoordenadasComponent } from 'app/entities/coordenadas/coordenadas.component';
import { CoordenadasService } from 'app/entities/coordenadas/coordenadas.service';
import { Coordenadas } from 'app/shared/model/coordenadas.model';

describe('Component Tests', () => {
    describe('Coordenadas Management Component', () => {
        let comp: CoordenadasComponent;
        let fixture: ComponentFixture<CoordenadasComponent>;
        let service: CoordenadasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [CoordenadasComponent],
                providers: []
            })
                .overrideTemplate(CoordenadasComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CoordenadasComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoordenadasService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Coordenadas(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.coordenadas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
