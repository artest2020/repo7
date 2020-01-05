import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { RespostaDetailComponent } from 'app/entities/resposta/resposta-detail.component';
import { Resposta } from 'app/shared/model/resposta.model';

describe('Component Tests', () => {
  describe('Resposta Management Detail Component', () => {
    let comp: RespostaDetailComponent;
    let fixture: ComponentFixture<RespostaDetailComponent>;
    const route = ({ data: of({ resposta: new Resposta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [RespostaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RespostaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RespostaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resposta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resposta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
