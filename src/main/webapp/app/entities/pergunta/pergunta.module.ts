import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Repo7SharedModule } from 'app/shared/shared.module';
import { PerguntaComponent } from './pergunta.component';
import { PerguntaDetailComponent } from './pergunta-detail.component';
import { PerguntaUpdateComponent } from './pergunta-update.component';
import { PerguntaDeleteDialogComponent } from './pergunta-delete-dialog.component';
import { perguntaRoute } from './pergunta.route';

@NgModule({
  imports: [Repo7SharedModule, RouterModule.forChild(perguntaRoute)],
  declarations: [PerguntaComponent, PerguntaDetailComponent, PerguntaUpdateComponent, PerguntaDeleteDialogComponent],
  entryComponents: [PerguntaDeleteDialogComponent]
})
export class Repo7PerguntaModule {}
