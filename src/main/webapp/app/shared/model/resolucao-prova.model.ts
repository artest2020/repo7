import { Moment } from 'moment';

export interface IResolucaoProva {
  id?: number;
  dataHoraInicio?: Moment;
  dataHoraFim?: Moment;
}

export class ResolucaoProva implements IResolucaoProva {
  constructor(public id?: number, public dataHoraInicio?: Moment, public dataHoraFim?: Moment) {}
}
