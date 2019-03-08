import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';

type EntityResponseType = HttpResponse<IPostoTrabalho>;
type EntityArrayResponseType = HttpResponse<IPostoTrabalho[]>;

@Injectable({ providedIn: 'root' })
export class PostoTrabalhoService {
    public resourceUrl = SERVER_API_URL + 'api/posto-trabalhos';

    constructor(protected http: HttpClient) {}

    create(postoTrabalho: IPostoTrabalho): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(postoTrabalho);
        return this.http
            .post<IPostoTrabalho>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(postoTrabalho: IPostoTrabalho): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(postoTrabalho);
        return this.http
            .put<IPostoTrabalho>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPostoTrabalho>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPostoTrabalho[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(postoTrabalho: IPostoTrabalho): IPostoTrabalho {
        const copy: IPostoTrabalho = Object.assign({}, postoTrabalho, {
            dataInicio: postoTrabalho.dataInicio != null && postoTrabalho.dataInicio.isValid() ? postoTrabalho.dataInicio.toJSON() : null,
            dataFim: postoTrabalho.dataFim != null && postoTrabalho.dataFim.isValid() ? postoTrabalho.dataFim.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataInicio = res.body.dataInicio != null ? moment(res.body.dataInicio) : null;
            res.body.dataFim = res.body.dataFim != null ? moment(res.body.dataFim) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((postoTrabalho: IPostoTrabalho) => {
                postoTrabalho.dataInicio = postoTrabalho.dataInicio != null ? moment(postoTrabalho.dataInicio) : null;
                postoTrabalho.dataFim = postoTrabalho.dataFim != null ? moment(postoTrabalho.dataFim) : null;
            });
        }
        return res;
    }
}
