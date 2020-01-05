import { TipoResposta } from 'app/shared/model/enumerations/tipo-resposta.model';
import { TipoVF } from 'app/shared/model/enumerations/tipo-vf.model';

export interface IResposta {
  id?: number;
  tipo?: TipoResposta;
  valorVF?: TipoVF;
  valorTexto?: string;
  valorN?: number;
  valorZ?: number;
  valorQ?: number;
}

export class Resposta implements IResposta {
  constructor(
    public id?: number,
    public tipo?: TipoResposta,
    public valorVF?: TipoVF,
    public valorTexto?: string,
    public valorN?: number,
    public valorZ?: number,
    public valorQ?: number
  ) {}
}
