import { ILocalizacao } from 'app/shared/model/localizacao.model';
import { IFuncionario } from 'app/shared/model/funcionario.model';
import { IEmpresa } from 'app/shared/model/empresa.model';

export interface IEstabelecimento {
    id?: number;
    designacao?: string;
    localizacao?: ILocalizacao;
    funcionarios?: IFuncionario[];
    empresa?: IEmpresa;
}

export class Estabelecimento implements IEstabelecimento {
    constructor(
        public id?: number,
        public designacao?: string,
        public localizacao?: ILocalizacao,
        public funcionarios?: IFuncionario[],
        public empresa?: IEmpresa
    ) {}
}
