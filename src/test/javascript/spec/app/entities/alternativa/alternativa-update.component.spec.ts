import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { AlternativaUpdateComponent } from 'app/entities/alternativa/alternativa-update.component';
import { AlternativaService } from 'app/entities/alternativa/alternativa.service';
import { Alternativa } from 'app/shared/model/alternativa.model';

describe('Component Tests', () => {
  describe('Alternativa Management Update Component', () => {
    let comp: AlternativaUpdateComponent;
    let fixture: ComponentFixture<AlternativaUpdateComponent>;
    let service: AlternativaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [AlternativaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlternativaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlternativaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlternativaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Alternativa(123);
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
        const entity = new Alternativa();
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
