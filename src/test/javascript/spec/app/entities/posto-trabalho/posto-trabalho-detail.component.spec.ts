/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpmtshtTestModule } from '../../../test.module';
import { PostoTrabalhoDetailComponent } from 'app/entities/posto-trabalho/posto-trabalho-detail.component';
import { PostoTrabalho } from 'app/shared/model/posto-trabalho.model';

describe('Component Tests', () => {
    describe('PostoTrabalho Management Detail Component', () => {
        let comp: PostoTrabalhoDetailComponent;
        let fixture: ComponentFixture<PostoTrabalhoDetailComponent>;
        const route = ({ data: of({ postoTrabalho: new PostoTrabalho(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [PostoTrabalhoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PostoTrabalhoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PostoTrabalhoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.postoTrabalho).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
