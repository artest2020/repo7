import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { QuestaoUpdateComponent } from 'app/entities/questao/questao-update.component';
import { QuestaoService } from 'app/entities/questao/questao.service';
import { Questao } from 'app/shared/model/questao.model';

describe('Component Tests', () => {
  describe('Questao Management Update Component', () => {
    let comp: QuestaoUpdateComponent;
    let fixture: ComponentFixture<QuestaoUpdateComponent>;
    let service: QuestaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [QuestaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(QuestaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Questao(123);
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
        const entity = new Questao();
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
