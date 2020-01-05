import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Repo7SharedModule } from 'app/shared/shared.module';
import { Repo7CoreModule } from 'app/core/core.module';
import { Repo7AppRoutingModule } from './app-routing.module';
import { Repo7HomeModule } from './home/home.module';
import { Repo7EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Repo7SharedModule,
    Repo7CoreModule,
    Repo7HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Repo7EntityModule,
    Repo7AppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class Repo7AppModule {}
