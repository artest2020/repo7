import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { AlternativaDetailComponent } from 'app/entities/alternativa/alternativa-detail.component';
import { Alternativa } from 'app/shared/model/alternativa.model';

describe('Component Tests', () => {
  describe('Alternativa Management Detail Component', () => {
    let comp: AlternativaDetailComponent;
    let fixture: ComponentFixture<AlternativaDetailComponent>;
    const route = ({ data: of({ alternativa: new Alternativa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [AlternativaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlternativaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlternativaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alternativa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alternativa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
