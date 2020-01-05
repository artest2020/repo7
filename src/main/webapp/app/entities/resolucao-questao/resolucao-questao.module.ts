import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Repo7SharedModule } from 'app/shared/shared.module';
import { ResolucaoQuestaoComponent } from './resolucao-questao.component';
import { ResolucaoQuestaoDetailComponent } from './resolucao-questao-detail.component';
import { ResolucaoQuestaoUpdateComponent } from './resolucao-questao-update.component';
import { ResolucaoQuestaoDeleteDialogComponent } from './resolucao-questao-delete-dialog.component';
import { resolucaoQuestaoRoute } from './resolucao-questao.route';

@NgModule({
  imports: [Repo7SharedModule, RouterModule.forChild(resolucaoQuestaoRoute)],
  declarations: [
    ResolucaoQuestaoComponent,
    ResolucaoQuestaoDetailComponent,
    ResolucaoQuestaoUpdateComponent,
    ResolucaoQuestaoDeleteDialogComponent
  ],
  entryComponents: [ResolucaoQuestaoDeleteDialogComponent]
})
export class Repo7ResolucaoQuestaoModule {}
