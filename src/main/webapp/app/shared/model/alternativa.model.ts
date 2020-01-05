import { IFrase } from 'app/shared/model/frase.model';
import { IQuestao } from 'app/shared/model/questao.model';

export interface IAlternativa {
  id?: number;
  ordem?: number;
  listaFrases?: IFrase[];
  questao?: IQuestao;
}

export class Alternativa implements IAlternativa {
  constructor(public id?: number, public ordem?: number, public listaFrases?: IFrase[], public questao?: IQuestao) {}
}
