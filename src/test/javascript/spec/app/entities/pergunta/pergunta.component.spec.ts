import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { PerguntaComponent } from 'app/entities/pergunta/pergunta.component';
import { PerguntaService } from 'app/entities/pergunta/pergunta.service';
import { Pergunta } from 'app/shared/model/pergunta.model';

describe('Component Tests', () => {
  describe('Pergunta Management Component', () => {
    let comp: PerguntaComponent;
    let fixture: ComponentFixture<PerguntaComponent>;
    let service: PerguntaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [PerguntaComponent],
        providers: []
      })
        .overrideTemplate(PerguntaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerguntaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerguntaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Pergunta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perguntas && comp.perguntas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
