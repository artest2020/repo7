import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { EditalUpdateComponent } from 'app/entities/edital/edital-update.component';
import { EditalService } from 'app/entities/edital/edital.service';
import { Edital } from 'app/shared/model/edital.model';

describe('Component Tests', () => {
  describe('Edital Management Update Component', () => {
    let comp: EditalUpdateComponent;
    let fixture: ComponentFixture<EditalUpdateComponent>;
    let service: EditalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [EditalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EditalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EditalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EditalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Edital(123);
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
        const entity = new Edital();
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
