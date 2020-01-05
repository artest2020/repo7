import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { FraseUpdateComponent } from 'app/entities/frase/frase-update.component';
import { FraseService } from 'app/entities/frase/frase.service';
import { Frase } from 'app/shared/model/frase.model';

describe('Component Tests', () => {
  describe('Frase Management Update Component', () => {
    let comp: FraseUpdateComponent;
    let fixture: ComponentFixture<FraseUpdateComponent>;
    let service: FraseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [FraseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FraseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FraseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FraseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Frase(123);
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
        const entity = new Frase();
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
