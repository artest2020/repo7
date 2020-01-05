import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Repo7TestModule } from '../../../test.module';
import { ResolucaoQuestaoUpdateComponent } from 'app/entities/resolucao-questao/resolucao-questao-update.component';
import { ResolucaoQuestaoService } from 'app/entities/resolucao-questao/resolucao-questao.service';
import { ResolucaoQuestao } from 'app/shared/model/resolucao-questao.model';

describe('Component Tests', () => {
  describe('ResolucaoQuestao Management Update Component', () => {
    let comp: ResolucaoQuestaoUpdateComponent;
    let fixture: ComponentFixture<ResolucaoQuestaoUpdateComponent>;
    let service: ResolucaoQuestaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Repo7TestModule],
        declarations: [ResolucaoQuestaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResolucaoQuestaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResolucaoQuestaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResolucaoQuestaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResolucaoQuestao(123);
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
        const entity = new ResolucaoQuestao();
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
