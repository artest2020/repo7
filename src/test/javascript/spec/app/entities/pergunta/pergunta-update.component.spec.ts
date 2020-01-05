import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { PerguntaUpdateComponent } from 'app/entities/pergunta/pergunta-update.component';
import { PerguntaService } from 'app/entities/pergunta/pergunta.service';
import { Pergunta } from 'app/shared/model/pergunta.model';

describe('Component Tests', () => {
  describe('Pergunta Management Update Component', () => {
    let comp: PerguntaUpdateComponent;
    let fixture: ComponentFixture<PerguntaUpdateComponent>;
    let service: PerguntaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [PerguntaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PerguntaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerguntaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerguntaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pergunta(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pergunta();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
