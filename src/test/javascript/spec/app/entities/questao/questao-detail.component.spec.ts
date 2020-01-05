import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { QuestaoDetailComponent } from 'app/entities/questao/questao-detail.component';
import { Questao } from 'app/shared/model/questao.model';

describe('Component Tests', () => {
  describe('Questao Management Detail Component', () => {
    let comp: QuestaoDetailComponent;
    let fixture: ComponentFixture<QuestaoDetailComponent>;
    const route = ({ data: of({ questao: new Questao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [QuestaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(QuestaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
