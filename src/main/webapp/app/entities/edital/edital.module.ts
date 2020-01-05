import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Repo7SharedModule } from 'app/shared/shared.module';
import { EditalComponent } from './edital.component';
import { EditalDetailComponent } from './edital-detail.component';
import { EditalUpdateComponent } from './edital-update.component';
import { EditalDeleteDialogComponent } from './edital-delete-dialog.component';
import { editalRoute } from './edital.route';

@NgModule({
  imports: [Repo7SharedModule, RouterModule.forChild(editalRoute)],
  declarations: [EditalComponent, EditalDetailComponent, EditalUpdateComponent, EditalDeleteDialogComponent],
  entryComponents: [EditalDeleteDialogComponent]
})
export class Repo7EditalModule {}
