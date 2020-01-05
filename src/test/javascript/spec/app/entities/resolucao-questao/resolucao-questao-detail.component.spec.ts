import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { ResolucaoQuestaoDetailComponent } from 'app/entities/resolucao-questao/resolucao-questao-detail.component';
import { ResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';

describe('Component Tests', () => {
  describe('ResolucaoQuestao Management Detail Component', () => {
    let comp: ResolucaoQuestaoDetailComponent;
    let fixture: ComponentFixture<ResolucaoQuestaoDetailComponent>;
    const route = ({ data: of({ resolucaoQuestao: new ResolucaoQuestao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [ResolucaoQuestaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResolucaoQuestaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResolucaoQuestaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resolucaoQuestao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resolucaoQuestao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
