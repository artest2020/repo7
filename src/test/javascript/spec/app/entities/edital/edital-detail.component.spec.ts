import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { EditalDetailComponent } from 'app/entities/edital/edital-detail.component';
import { Edital } from 'app/shared/model/edital.model';

describe('Component Tests', () => {
  describe('Edital Management Detail Component', () => {
    let comp: EditalDetailComponent;
    let fixture: ComponentFixture<EditalDetailComponent>;
    const route = ({ data: of({ edital: new Edital(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [EditalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EditalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EditalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load edital on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.edital).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
