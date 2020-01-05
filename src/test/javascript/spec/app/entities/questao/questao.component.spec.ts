import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { QuestaoComponent } from 'app/entities/questao/questao.component';
import { QuestaoService } from 'app/entities/questao/questao.service';
import { Questao } from 'app/shared/model/questao.model';

describe('Component Tests', () => {
  describe('Questao Management Component', () => {
    let comp: QuestaoComponent;
    let fixture: ComponentFixture<QuestaoComponent>;
    let service: QuestaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [QuestaoComponent],
        providers: []
      })
        .overrideTemplate(QuestaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Questao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questaos && comp.questaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
