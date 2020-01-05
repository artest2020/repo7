import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { AlternativaComponent } from 'app/entities/alternativa/alternativa.component';
import { AlternativaService } from 'app/entities/alternativa/alternativa.service';
import { Alternativa } from 'app/shared/model/alternativa.model';

describe('Component Tests', () => {
  describe('Alternativa Management Component', () => {
    let comp: AlternativaComponent;
    let fixture: ComponentFixture<AlternativaComponent>;
    let service: AlternativaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [AlternativaComponent],
        providers: []
      })
        .overrideTemplate(AlternativaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlternativaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlternativaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Alternativa(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alternativas && comp.alternativas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
