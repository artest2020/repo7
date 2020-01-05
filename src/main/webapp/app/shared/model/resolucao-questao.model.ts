import { Moment } from 'moment';

export interface IResolucaoQuestao {
  id?: number;
  dataHoraInicio?: Moment;
  dataHoraFim?: Moment;
}

export class ResolucaoQuestao implements IResolucaoQuestao {
  constructor(public id?: number, public dataHoraInicio?: Moment, public dataHoraFim?: Moment) {}
}
