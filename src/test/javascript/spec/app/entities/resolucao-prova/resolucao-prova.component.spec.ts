import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { ResolucaoProvaComponent } from 'app/entities/resolucao-prova/resolucao-prova.component';
import { ResolucaoProvaService } from 'app/entities/resolucao-prova/resolucao-prova.service';
import { ResolucaoProva } from 'app/shared/model/resolucao-prova.model';

describe('Component Tests', () => {
  describe('ResolucaoProva Management Component', () => {
    let comp: ResolucaoProvaComponent;
    let fixture: ComponentFixture<ResolucaoProvaComponent>;
    let service: ResolucaoProvaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [ResolucaoProvaComponent],
        providers: []
      })
        .overrideTemplate(ResolucaoProvaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResolucaoProvaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResolucaoProvaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResolucaoProva(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.resolucaoProva && comp.resolucaoProva[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
