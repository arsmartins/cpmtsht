import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CpmtshtSharedModule } from 'app/shared';
import {
    LocalizacaoComponent,
    LocalizacaoDetailComponent,
    LocalizacaoUpdateComponent,
    LocalizacaoDeletePopupComponent,
    LocalizacaoDeleteDialogComponent,
    localizacaoRoute,
    localizacaoPopupRoute
} from './';

const ENTITY_STATES = [...localizacaoRoute, ...localizacaoPopupRoute];

@NgModule({
    imports: [CpmtshtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LocalizacaoComponent,
        LocalizacaoDetailComponent,
        LocalizacaoUpdateComponent,
        LocalizacaoDeleteDialogComponent,
        LocalizacaoDeletePopupComponent
    ],
    entryComponents: [LocalizacaoComponent, LocalizacaoUpdateComponent, LocalizacaoDeleteDialogComponent, LocalizacaoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmtshtLocalizacaoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
