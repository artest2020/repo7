import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { RespostaComponent } from 'app/entities/resposta/resposta.component';
import { RespostaService } from 'app/entities/resposta/resposta.service';
import { Resposta } from 'app/shared/model/resposta.model';

describe('Component Tests', () => {
  describe('Resposta Management Component', () => {
    let comp: RespostaComponent;
    let fixture: ComponentFixture<RespostaComponent>;
    let service: RespostaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [RespostaComponent],
        providers: []
      })
        .overrideTemplate(RespostaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RespostaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RespostaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Resposta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.respostas && comp.respostas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
