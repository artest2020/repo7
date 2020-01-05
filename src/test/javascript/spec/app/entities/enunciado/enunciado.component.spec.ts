import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { EnunciadoComponent } from 'app/entities/enunciado/enunciado.component';
import { EnunciadoService } from 'app/entities/enunciado/enunciado.service';
import { Enunciado } from 'app/shared/model/enunciado.model';

describe('Component Tests', () => {
  describe('Enunciado Management Component', () => {
    let comp: EnunciadoComponent;
    let fixture: ComponentFixture<EnunciadoComponent>;
    let service: EnunciadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [EnunciadoComponent],
        providers: []
      })
        .overrideTemplate(EnunciadoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnunciadoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnunciadoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Enunciado(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.enunciados && comp.enunciados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
