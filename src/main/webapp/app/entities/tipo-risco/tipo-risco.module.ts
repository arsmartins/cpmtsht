import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CpmtshtSharedModule } from 'app/shared';
import {
    TipoRiscoComponent,
    TipoRiscoDetailComponent,
    TipoRiscoUpdateComponent,
    TipoRiscoDeletePopupComponent,
    TipoRiscoDeleteDialogComponent,
    tipoRiscoRoute,
    tipoRiscoPopupRoute
} from './';

const ENTITY_STATES = [...tipoRiscoRoute, ...tipoRiscoPopupRoute];

@NgModule({
    imports: [CpmtshtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoRiscoComponent,
        TipoRiscoDetailComponent,
        TipoRiscoUpdateComponent,
        TipoRiscoDeleteDialogComponent,
        TipoRiscoDeletePopupComponent
    ],
    entryComponents: [TipoRiscoComponent, TipoRiscoUpdateComponent, TipoRiscoDeleteDialogComponent, TipoRiscoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmtshtTipoRiscoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
