import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { EditalComponent } from 'app/entities/edital/edital.component';
import { EditalService } from 'app/entities/edital/edital.service';
import { Edital } from 'app/shared/model/edital.model';

describe('Component Tests', () => {
  describe('Edital Management Component', () => {
    let comp: EditalComponent;
    let fixture: ComponentFixture<EditalComponent>;
    let service: EditalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [EditalComponent],
        providers: []
      })
        .overrideTemplate(EditalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EditalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EditalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Edital(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.editals && comp.editals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
