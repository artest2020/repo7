export interface IEdital {
  id?: number;
  descricao?: string;
}

export class Edital implements IEdital {
  constructor(public id?: number, public descricao?: string) {}
}
