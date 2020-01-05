import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { EnunciadoUpdateComponent } from 'app/entities/enunciado/enunciado-update.component';
import { EnunciadoService } from 'app/entities/enunciado/enunciado.service';
import { Enunciado } from 'app/shared/model/enunciado.model';

describe('Component Tests', () => {
  describe('Enunciado Management Update Component', () => {
    let comp: EnunciadoUpdateComponent;
    let fixture: ComponentFixture<EnunciadoUpdateComponent>;
    let service: EnunciadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [EnunciadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EnunciadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EnunciadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EnunciadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Enunciado(123);
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
        const entity = new Enunciado();
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
