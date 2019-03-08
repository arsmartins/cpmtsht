import { Moment } from 'moment';
import { IFuncionario } from 'app/shared/model/funcionario.model';
import { IRisco } from 'app/shared/model/risco.model';

export interface IPostoTrabalho {
    id?: number;
    designacao?: string;
    dataInicio?: Moment;
    dataFim?: Moment;
    funcionario?: IFuncionario;
    riscos?: IRisco[];
}

export class PostoTrabalho implements IPostoTrabalho {
    constructor(
        public id?: number,
        public designacao?: string,
        public dataInicio?: Moment,
        public dataFim?: Moment,
        public funcionario?: IFuncionario,
        public riscos?: IRisco[]
    ) {}
}
