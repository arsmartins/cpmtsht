import { ITipoRisco } from 'app/shared/model/tipo-risco.model';
import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';

export interface IRisco {
    id?: number;
    title?: string;
    description?: string;
    tipo?: ITipoRisco;
    postoTrabalho?: IPostoTrabalho;
}

export class Risco implements IRisco {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public tipo?: ITipoRisco,
        public postoTrabalho?: IPostoTrabalho
    ) {}
}
