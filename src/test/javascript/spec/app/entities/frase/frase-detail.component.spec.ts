import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { FraseDetailComponent } from 'app/entities/frase/frase-detail.component';
import { Frase } from 'app/shared/model/frase.model';

describe('Component Tests', () => {
  describe('Frase Management Detail Component', () => {
    let comp: FraseDetailComponent;
    let fixture: ComponentFixture<FraseDetailComponent>;
    const route = ({ data: of({ frase: new Frase(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [FraseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FraseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FraseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load frase on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.frase).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
