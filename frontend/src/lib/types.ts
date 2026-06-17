// Tipos que espelham os DTOs do backend (HospedeResponse e aninhados).
// Os nomes de campo são idênticos ao JSON do backend para mapear direto —
// inclusive `dtaNascimento`, com a grafia usada lá.

export interface Telefone {
  ddd: string;
  numero: string;
}

export interface Endereco {
  logradouro: string;
  numero: string;
  complemento?: string;
  bairro: string;
  cep: string;
  cidade: string;
  estado: string;
}

export interface Hospede {
  id: string;
  nome: string;
  cpf: string;
  dtaNascimento: string;
  email: string;
  telefone: Telefone;
  endereco: Endereco;
}

export interface LoginResponse {
  token: string;
  tipo: string;
}

// Corpo de POST /hospedes. Atenção: aqui a data chama-se `dtNascimento`
// (a resposta usa `dtaNascimento`).
export interface CadastrarHospedeRequest {
  nome: string;
  cpf: string;
  dtNascimento: string;
  email: string;
  senha: string;
  aceitouTermos: boolean;
  telefone: Telefone;
  endereco: Endereco;
}
