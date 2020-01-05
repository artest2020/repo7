import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { ResolucaoProvaUpdateComponent } from 'app/entities/resolucao-prova/resolucao-prova-update.component';
import { ResolucaoProvaService } from 'app/entities/resolucao-prova/resolucao-prova.service';
import { ResolucaoProva } from 'app/shared/model/resolucao-prova.model';

describe('Component Tests', () => {
  describe('ResolucaoProva Management Update Component', () => {
    let comp: ResolucaoProvaUpdateComponent;
    let fixture: ComponentFixture<ResolucaoProvaUpdateComponent>;
    let service: ResolucaoProvaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [ResolucaoProvaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResolucaoProvaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResolucaoProvaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResolucaoProvaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResolucaoProva(123);
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
        const entity = new ResolucaoProva();
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
