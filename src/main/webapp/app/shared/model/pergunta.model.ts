export interface IPergunta {
  id?: number;
  descricao?: string;
}

export class Pergunta implements IPergunta {
  constructor(public id?: number, public descricao?: string) {}
}
