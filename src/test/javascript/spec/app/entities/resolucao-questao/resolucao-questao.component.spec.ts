import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { ResolucaoQuestaoComponent } from 'app/entities/resolucao-questao/resolucao-questao.component';
import { ResolucaoQuestaoService } from 'app/entities/resolucao-questao/resolucao-questao.service';
import { ResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';

describe('Component Tests', () => {
  describe('ResolucaoQuestao Management Component', () => {
    let comp: ResolucaoQuestaoComponent;
    let fixture: ComponentFixture<ResolucaoQuestaoComponent>;
    let service: ResolucaoQuestaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [ResolucaoQuestaoComponent],
        providers: []
      })
        .overrideTemplate(ResolucaoQuestaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResolucaoQuestaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResolucaoQuestaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResolucaoQuestao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.resolucaoQuestaos && comp.resolucaoQuestaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
