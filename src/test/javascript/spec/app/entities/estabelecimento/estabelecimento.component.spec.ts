/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpmtshtTestModule } from '../../../test.module';
import { EstabelecimentoComponent } from 'app/entities/estabelecimento/estabelecimento.component';
import { EstabelecimentoService } from 'app/entities/estabelecimento/estabelecimento.service';
import { Estabelecimento } from 'app/shared/model/estabelecimento.model';

describe('Component Tests', () => {
    describe('Estabelecimento Management Component', () => {
        let comp: EstabelecimentoComponent;
        let fixture: ComponentFixture<EstabelecimentoComponent>;
        let service: EstabelecimentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpmtshtTestModule],
                declarations: [EstabelecimentoComponent],
                providers: []
            })
                .overrideTemplate(EstabelecimentoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstabelecimentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstabelecimentoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Estabelecimento(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.estabelecimentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
