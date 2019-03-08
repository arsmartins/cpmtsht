import { ICoordenadas } from 'app/shared/model/coordenadas.model';

export interface ILocalizacao {
    id?: number;
    streetAddress?: string;
    postalCode?: string;
    city?: string;
    district?: string;
    country?: string;
    coordenadas?: ICoordenadas;
}

export class Localizacao implements ILocalizacao {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public postalCode?: string,
        public city?: string,
        public district?: string,
        public country?: string,
        public coordenadas?: ICoordenadas
    ) {}
}
