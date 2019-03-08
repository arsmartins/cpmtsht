/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CpmtshtTestModule } from '../../../test.module';
import { PostoTrabalhoUpdateComponent } from 'app/entities/posto-trabalho/posto-trabalho-update.component';
import { PostoTrabalhoService } from 'app/entities/posto-trabalho/posto-trabalho.service';
import { PostoTrabalho } from 'app/shared/model/posto-trabalho.model';

describe('Component Tests', () => {
    describe('PostoTrabalho Management Update Component', () => {
        let comp: PostoTrabalhoUpdateComponent;
        let fixture: ComponentFixture<PostoTrabalhoUpdateComponent>;
        let service: PostoTrabalhoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [PostoTrabalhoUpdateComponent]
            })
                .overrideTemplate(PostoTrabalhoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PostoTrabalhoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PostoTrabalhoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PostoTrabalho(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.postoTrabalho = entity;
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
                    const entity = new PostoTrabalho();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.postoTrabalho = entity;
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
