import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ResolucaoProvaService } from 'app/entities/resolucao-prova/resolucao-prova.service';
import { IResolucaoProva, ResolucaoProva } from 'app/shared/model/resolucao-prova.model';

describe('Service Tests', () => {
  describe('ResolucaoProva Service', () => {
    let injector: TestBed;
    let service: ResolucaoProvaService;
    let httpMock: HttpTestingController;
    let elemDefault: IResolucaoProva;
    let expectedResult: IResolucaoProva | IResolucaoProva[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ResolucaoProvaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ResolucaoProva(0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataHoraInicio: currentDate.format(DATE_TIME_FORMAT),
            dataHoraFim: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ResolucaoProva', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataHoraInicio: currentDate.format(DATE_TIME_FORMAT),
            dataHoraFim: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataHoraInicio: currentDate,
            dataHoraFim: currentDate
          },
          returnedFromService
        );
        service
          .create(new ResolucaoProva())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ResolucaoProva', () => {
        const returnedFromService = Object.assign(
          {
            dataHoraInicio: currentDate.format(DATE_TIME_FORMAT),
            dataHoraFim: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataHoraInicio: currentDate,
            dataHoraFim: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ResolucaoProva', () => {
        const returnedFromService = Object.assign(
          {
            dataHoraInicio: currentDate.format(DATE_TIME_FORMAT),
            dataHoraFim: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataHoraInicio: currentDate,
            dataHoraFim: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ResolucaoProva', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
