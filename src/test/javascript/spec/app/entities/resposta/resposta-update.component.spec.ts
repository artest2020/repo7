import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { RespostaUpdateComponent } from 'app/entities/resposta/resposta-update.component';
import { RespostaService } from 'app/entities/resposta/resposta.service';
import { Resposta } from 'app/shared/model/resposta.model';

describe('Component Tests', () => {
  describe('Resposta Management Update Component', () => {
    let comp: RespostaUpdateComponent;
    let fixture: ComponentFixture<RespostaUpdateComponent>;
    let service: RespostaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [RespostaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RespostaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RespostaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RespostaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Resposta(123);
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
        const entity = new Resposta();
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
