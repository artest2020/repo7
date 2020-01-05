export interface IEnunciado {
  id?: number;
  descricao?: string;
}

export class Enunciado implements IEnunciado {
  constructor(public id?: number, public descricao?: string) {}
}
