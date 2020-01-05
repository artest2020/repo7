import { IEnunciado } from 'app/shared/model/enunciado.model';
import { IPergunta } from 'app/shared/model/pergunta.model';
import { IAlternativa } from 'app/shared/model/alternativa.model';
import { IProva } from 'app/shared/model/prova.model';

export interface IQuestao {
  id?: number;
  ordem?: number;
  enunciado?: IEnunciado;
  pergunta?: IPergunta;
  listaAlternativas?: IAlternativa[];
  prova?: IProva;
}

export class Questao implements IQuestao {
  constructor(
    public id?: number,
    public ordem?: number,
    public enunciado?: IEnunciado,
    public pergunta?: IPergunta,
    public listaAlternativas?: IAlternativa[],
    public prova?: IProva
  ) {}
}
