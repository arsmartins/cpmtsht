/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PostoTrabalhoService } from 'app/entities/posto-trabalho/posto-trabalho.service';
import { IPostoTrabalho, PostoTrabalho } from 'app/shared/model/posto-trabalho.model';

describe('Service Tests', () => {
    describe('PostoTrabalho Service', () => {
        let injector: TestBed;
        let service: PostoTrabalhoService;
        let httpMock: HttpTestingController;
        let elemDefault: IPostoTrabalho;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PostoTrabalhoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PostoTrabalho(0, 'AAAAAAA', currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataInicio: currentDate.format(DATE_TIME_FORMAT),
                        dataFim: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a PostoTrabalho', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataInicio: currentDate.format(DATE_TIME_FORMAT),
                        dataFim: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataInicio: currentDate,
                        dataFim: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PostoTrabalho(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PostoTrabalho', async () => {
                const returnedFromService = Object.assign(
                    {
                        designacao: 'BBBBBB',
                        dataInicio: currentDate.format(DATE_TIME_FORMAT),
                        dataFim: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataInicio: currentDate,
                        dataFim: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of PostoTrabalho', async () => {
                const returnedFromService = Object.assign(
                    {
                        designacao: 'BBBBBB',
                        dataInicio: currentDate.format(DATE_TIME_FORMAT),
                        dataFim: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataInicio: currentDate,
                        dataFim: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a PostoTrabalho', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
