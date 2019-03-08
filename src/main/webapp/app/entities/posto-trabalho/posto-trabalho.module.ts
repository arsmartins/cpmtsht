import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CpmtshtSharedModule } from 'app/shared';
import {
    PostoTrabalhoComponent,
    PostoTrabalhoDetailComponent,
    PostoTrabalhoUpdateComponent,
    PostoTrabalhoDeletePopupComponent,
    PostoTrabalhoDeleteDialogComponent,
    postoTrabalhoRoute,
    postoTrabalhoPopupRoute
} from './';

const ENTITY_STATES = [...postoTrabalhoRoute, ...postoTrabalhoPopupRoute];

@NgModule({
    imports: [CpmtshtSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PostoTrabalhoComponent,
        PostoTrabalhoDetailComponent,
        PostoTrabalhoUpdateComponent,
        PostoTrabalhoDeleteDialogComponent,
        PostoTrabalhoDeletePopupComponent
    ],
    entryComponents: [
        PostoTrabalhoComponent,
        PostoTrabalhoUpdateComponent,
        PostoTrabalhoDeleteDialogComponent,
        PostoTrabalhoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpmtshtPostoTrabalhoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
