import { Moment } from 'moment';
import { IInstituicao } from 'app/shared/model/instituicao.model';
import { IEdital } from 'app/shared/model/edital.model';
import { IQuestao } from 'app/shared/model/questao.model';

export interface IProva {
  id?: number;
  ano?: number;
  cidade?: string;
  dataHoraInicio?: Moment;
  dataHoraFim?: Moment;
  instituicao?: IInstituicao;
  edital?: IEdital;
  listaQuestaos?: IQuestao[];
}

export class Prova implements IProva {
  constructor(
    public id?: number,
    public ano?: number,
    public cidade?: string,
    public dataHoraInicio?: Moment,
    public dataHoraFim?: Moment,
    public instituicao?: IInstituicao,
    public edital?: IEdital,
    public listaQuestaos?: IQuestao[]
  ) {}
}
