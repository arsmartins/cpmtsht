import { Moment } from 'moment';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { IFuncionario } from 'app/shared/model/funcionario.model';

export const enum Language {
    PORTUGUESE = 'PORTUGUESE',
    FRENCH = 'FRENCH',
    ENGLISH = 'ENGLISH',
    SPANISH = 'SPANISH'
}

export interface IEmpresa {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    language?: Language;
    estabelecimentos?: IEstabelecimento[];
    funcionarios?: IFuncionario[];
}

export class Empresa implements IEmpresa {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public language?: Language,
        public estabelecimentos?: IEstabelecimento[],
        public funcionarios?: IFuncionario[]
    ) {}
}
