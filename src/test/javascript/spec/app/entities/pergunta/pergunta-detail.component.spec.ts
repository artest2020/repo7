import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { PerguntaDetailComponent } from 'app/entities/pergunta/pergunta-detail.component';
import { Pergunta } from 'app/shared/model/pergunta.model';

describe('Component Tests', () => {
  describe('Pergunta Management Detail Component', () => {
    let comp: PerguntaDetailComponent;
    let fixture: ComponentFixture<PerguntaDetailComponent>;
    const route = ({ data: of({ pergunta: new Pergunta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [PerguntaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PerguntaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerguntaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pergunta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pergunta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
