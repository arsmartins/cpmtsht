/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpmtshtTestModule } from '../../../test.module';
import { RiscoDetailComponent } from 'app/entities/risco/risco-detail.component';
import { Risco } from 'app/shared/model/risco.model';

describe('Component Tests', () => {
    describe('Risco Management Detail Component', () => {
        let comp: RiscoDetailComponent;
        let fixture: ComponentFixture<RiscoDetailComponent>;
        const route = ({ data: of({ risco: new Risco(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [RiscoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RiscoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RiscoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.risco).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
