import { IAlternativa } from 'app/shared/model/alternativa.model';
import { TipoVF } from 'app/shared/model/enumerations/tipo-vf.model';

export interface IFrase {
  id?: number;
  descricao?: string;
  verdadeira?: TipoVF;
  alternativa?: IAlternativa;
}

export class Frase implements IFrase {
  constructor(public id?: number, public descricao?: string, public verdadeira?: TipoVF, public alternativa?: IAlternativa) {}
}
