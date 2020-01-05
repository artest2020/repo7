import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { ProvaComponent } from 'app/entities/prova/prova.component';
import { ProvaService } from 'app/entities/prova/prova.service';
import { Prova } from 'app/shared/model/prova.model';

describe('Component Tests', () => {
  describe('Prova Management Component', () => {
    let comp: ProvaComponent;
    let fixture: ComponentFixture<ProvaComponent>;
    let service: ProvaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [ProvaComponent],
        providers: []
      })
        .overrideTemplate(ProvaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProvaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProvaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Prova(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prova && comp.prova[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
