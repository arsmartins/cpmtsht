import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CpmtshtSharedModule } from 'app/shared';
import {
    CoordenadasComponent,
    CoordenadasDetailComponent,
    CoordenadasUpdateComponent,
    CoordenadasDeletePopupComponent,
    CoordenadasDeleteDialogComponent,
    coordenadasRoute,
    coordenadasPopupRoute
} from './';

const ENTITY_STATES = [...coordenadasRoute, ...coordenadasPopupRoute];

@NgModule({
    imports: [CpmtshtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CoordenadasComponent,
        CoordenadasDetailComponent,
        CoordenadasUpdateComponent,
        CoordenadasDeleteDialogComponent,
        CoordenadasDeletePopupComponent
    ],
    entryComponents: [CoordenadasComponent, CoordenadasUpdateComponent, CoordenadasDeleteDialogComponent, CoordenadasDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmtshtCoordenadasModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
