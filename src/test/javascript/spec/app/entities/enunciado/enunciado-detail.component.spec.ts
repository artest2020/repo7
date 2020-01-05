import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { EnunciadoDetailComponent } from 'app/entities/enunciado/enunciado-detail.component';
import { Enunciado } from 'app/shared/model/enunciado.model';

describe('Component Tests', () => {
  describe('Enunciado Management Detail Component', () => {
    let comp: EnunciadoDetailComponent;
    let fixture: ComponentFixture<EnunciadoDetailComponent>;
    const route = ({ data: of({ enunciado: new Enunciado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [EnunciadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EnunciadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnunciadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load enunciado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enunciado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
