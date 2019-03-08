import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoRisco } from 'app/shared/model/tipo-risco.model';

type EntityResponseType = HttpResponse<ITipoRisco>;
type EntityArrayResponseType = HttpResponse<ITipoRisco[]>;

@Injectable({ providedIn: 'root' })
export class TipoRiscoService {
    public resourceUrl = SERVER_API_URL + 'api/tipo-riscos';

    constructor(protected http: HttpClient) {}

    create(tipoRisco: ITipoRisco): Observable<EntityResponseType> {
        return this.http.post<ITipoRisco>(this.resourceUrl, tipoRisco, { observe: 'response' });
    }

    update(tipoRisco: ITipoRisco): Observable<EntityResponseType> {
        return this.http.put<ITipoRisco>(this.resourceUrl, tipoRisco, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoRisco>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoRisco[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
