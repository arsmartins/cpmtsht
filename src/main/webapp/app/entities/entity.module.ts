import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'localizacao',
                loadChildren: './localizacao/localizacao.module#CpmtshtLocalizacaoModule'
            },
            {
                path: 'coordenadas',
                loadChildren: './coordenadas/coordenadas.module#CpmtshtCoordenadasModule'
            },
            {
                path: 'estabelecimento',
                loadChildren: './estabelecimento/estabelecimento.module#CpmtshtEstabelecimentoModule'
            },
            {
                path: 'risco',
                loadChildren: './risco/risco.module#CpmtshtRiscoModule'
            },
            {
                path: 'funcionario',
                loadChildren: './funcionario/funcionario.module#CpmtshtFuncionarioModule'
            },
            {
                path: 'posto-trabalho',
                loadChildren: './posto-trabalho/posto-trabalho.module#CpmtshtPostoTrabalhoModule'
            },
            {
                path: 'empresa',
                loadChildren: './empresa/empresa.module#CpmtshtEmpresaModule'
            },
            {
                path: 'tipo-risco',
                loadChildren: './tipo-risco/tipo-risco.module#CpmtshtTipoRiscoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmtshtEntityModule {}
