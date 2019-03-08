import { Moment } from 'moment';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { IPostoTrabalho } from 'app/shared/model/posto-trabalho.model';
import { IEmpresa } from 'app/shared/model/empresa.model';

export interface IFuncionario {
    id?: number;
    fullName?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    telefone?: string;
    numCC?: string;
    numIF?: string;
    numSS?: string;
    dataEntrada?: Moment;
    dataSaida?: Moment;
    estabelecimento?: IEstabelecimento;
    postoTrabalhos?: IPostoTrabalho[];
    empresa?: IEmpresa;
}

export class Funcionario implements IFuncionario {
    constructor(
        public id?: number,
        public fullName?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public telefone?: string,
        public numCC?: string,
        public numIF?: string,
        public numSS?: string,
        public dataEntrada?: Moment,
        public dataSaida?: Moment,
        public estabelecimento?: IEstabelecimento,
        public postoTrabalhos?: IPostoTrabalho[],
        public empresa?: IEmpresa
    ) {}
}
