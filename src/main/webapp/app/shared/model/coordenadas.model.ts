export interface ICoordenadas {
    id?: number;
    latitude?: number;
    longitude?: number;
}

export class Coordenadas implements ICoordenadas {
    constructor(public id?: number, public latitude?: number, public longitude?: number) {}
}
