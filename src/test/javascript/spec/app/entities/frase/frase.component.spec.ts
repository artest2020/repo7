import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Repo7TestModule } from '../../../test.module';
import { FraseComponent } from 'app/entities/frase/frase.component';
import { FraseService } from 'app/entities/frase/frase.service';
import { Frase } from 'app/shared/model/frase.model';

describe('Component Tests', () => {
  describe('Frase Management Component', () => {
    let comp: FraseComponent;
    let fixture: ComponentFixture<FraseComponent>;
    let service: FraseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [FraseComponent],
        providers: []
      })
        .overrideTemplate(FraseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FraseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FraseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Frase(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.frases && comp.frases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
