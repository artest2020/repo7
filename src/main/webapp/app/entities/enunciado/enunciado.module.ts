import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Repo7SharedModule } from 'app/shared/shared.module';
import { EnunciadoComponent } from './enunciado.component';
import { EnunciadoDetailComponent } from './enunciado-detail.component';
import { EnunciadoUpdateComponent } from './enunciado-update.component';
import { EnunciadoDeleteDialogComponent } from './enunciado-delete-dialog.component';
import { enunciadoRoute } from './enunciado.route';

@NgModule({
  imports: [Repo7SharedModule, RouterModule.forChild(enunciadoRoute)],
  declarations: [EnunciadoComponent, EnunciadoDetailComponent, EnunciadoUpdateComponent, EnunciadoDeleteDialogComponent],
  entryComponents: [EnunciadoDeleteDialogComponent]
})
export class Repo7EnunciadoModule {}
