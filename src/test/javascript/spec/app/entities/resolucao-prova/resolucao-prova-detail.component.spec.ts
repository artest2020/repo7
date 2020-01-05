import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { ResolucaoProvaDetailComponent } from 'app/entities/resolucao-prova/resolucao-prova-detail.component';
import { ResolucaoProva } from 'app/shared/model/resolucao-prova.model';

describe('Component Tests', () => {
  describe('ResolucaoProva Management Detail Component', () => {
    let comp: ResolucaoProvaDetailComponent;
    let fixture: ComponentFixture<ResolucaoProvaDetailComponent>;
    const route = ({ data: of({ resolucaoProva: new ResolucaoProva(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [ResolucaoProvaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResolucaoProvaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResolucaoProvaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resolucaoProva on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resolucaoProva).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
