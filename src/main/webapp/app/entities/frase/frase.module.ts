import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Repo7SharedModule } from 'app/shared/shared.module';
import { FraseComponent } from './frase.component';
import { FraseDetailComponent } from './frase-detail.component';
import { FraseUpdateComponent } from './frase-update.component';
import { FraseDeleteDialogComponent } from './frase-delete-dialog.component';
import { fraseRoute } from './frase.route';

@NgModule({
  imports: [Repo7SharedModule, RouterModule.forChild(fraseRoute)],
  declarations: [FraseComponent, FraseDetailComponent, FraseUpdateComponent, FraseDeleteDialogComponent],
  entryComponents: [FraseDeleteDialogComponent]
})
export class Repo7FraseModule {}
