export interface IInstituicao {
  id?: number;
  nome?: string;
  sigla?: string;
}

export class Instituicao implements IInstituicao {
  constructor(public id?: number, public nome?: string, public sigla?: string) {}
}
