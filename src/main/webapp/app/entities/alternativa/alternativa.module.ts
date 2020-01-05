import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Repo7SharedModule } from 'app/shared/shared.module';
import { AlternativaComponent } from './alternativa.component';
import { AlternativaDetailComponent } from './alternativa-detail.component';
import { AlternativaUpdateComponent } from './alternativa-update.component';
import { AlternativaDeleteDialogComponent } from './alternativa-delete-dialog.component';
import { alternativaRoute } from './alternativa.route';

@NgModule({
  imports: [Repo7SharedModule, RouterModule.forChild(alternativaRoute)],
  declarations: [AlternativaComponent, AlternativaDetailComponent, AlternativaUpdateComponent, AlternativaDeleteDialogComponent],
  entryComponents: [AlternativaDeleteDialogComponent]
})
export class Repo7AlternativaModule {}
