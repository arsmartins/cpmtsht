export interface ITipoRisco {
    id?: number;
    designacao?: string;
}

export class TipoRisco implements ITipoRisco {
    constructor(public id?: number, public designacao?: string) {}
}
