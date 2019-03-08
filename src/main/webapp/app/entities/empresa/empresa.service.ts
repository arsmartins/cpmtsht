import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmpresa } from 'app/shared/model/empresa.model';

type EntityResponseType = HttpResponse<IEmpresa>;
type EntityArrayResponseType = HttpResponse<IEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaService {
    public resourceUrl = SERVER_API_URL + 'api/empresas';

    constructor(protected http: HttpClient) {}

    create(empresa: IEmpresa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(empresa);
        return this.http
            .post<IEmpresa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(empresa: IEmpresa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(empresa);
        return this.http
            .put<IEmpresa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(empresa: IEmpresa): IEmpresa {
        const copy: IEmpresa = Object.assign({}, empresa, {
            startDate: empresa.startDate != null && empresa.startDate.isValid() ? empresa.startDate.toJSON() : null,
            endDate: empresa.endDate != null && empresa.endDate.isValid() ? empresa.endDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((empresa: IEmpresa) => {
                empresa.startDate = empresa.startDate != null ? moment(empresa.startDate) : null;
                empresa.endDate = empresa.endDate != null ? moment(empresa.endDate) : null;
            });
        }
        return res;
    }
}
