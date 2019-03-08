/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpmtshtTestModule } from '../../../test.module';
import { TipoRiscoDetailComponent } from 'app/entities/tipo-risco/tipo-risco-detail.component';
import { TipoRisco } from 'app/shared/model/tipo-risco.model';

describe('Component Tests', () => {
    describe('TipoRisco Management Detail Component', () => {
        let comp: TipoRiscoDetailComponent;
        let fixture: ComponentFixture<TipoRiscoDetailComponent>;
        const route = ({ data: of({ tipoRisco: new TipoRisco(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [TipoRiscoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoRiscoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoRiscoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoRisco).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
