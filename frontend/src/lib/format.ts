import type { Endereco } from './types';

// Helpers de formatação para exibição (não alteram os dados em si).

// 11 dígitos -> 123.456.789-09
export function formatCpf(cpf: string): string {
  const d = cpf.replace(/\D/g, '').slice(0, 11);
  if (d.length !== 11) return cpf;
  return `${d.slice(0, 3)}.${d.slice(3, 6)}.${d.slice(6, 9)}-${d.slice(9)}`;
}

// 8 dígitos -> 01310-100
export function formatCep(cep: string): string {
  const d = cep.replace(/\D/g, '').slice(0, 8);
  if (d.length !== 8) return cep;
  return `${d.slice(0, 5)}-${d.slice(5)}`;
}

// "Marina Duarte" -> "MD" (primeira + última inicial)
export function initials(nome: string): string {
  const parts = nome.trim().split(/\s+/).filter(Boolean);
  if (parts.length === 0) return '';
  if (parts.length === 1) return parts[0].charAt(0).toUpperCase();
  const first = parts[0].charAt(0);
  const last = parts[parts.length - 1].charAt(0);
  return (first + last).toUpperCase();
}

// "Marina Duarte" -> "Marina"
export function firstName(nome: string): string {
  return nome.trim().split(/\s+/)[0] ?? '';
}

// Monta o endereço em uma linha, como no protótipo:
// "Av. Paulista, 1578 · Ap 92 — Bela Vista, São Paulo/SP · 01310-100"
export function formatEndereco(e: Endereco): string {
  const rua = [e.logradouro, e.numero].filter(Boolean).join(', ');
  const complemento = e.complemento ? ` · ${e.complemento}` : '';
  const local = `${e.bairro}, ${e.cidade}/${e.estado}`;
  return `${rua}${complemento} — ${local} · ${formatCep(e.cep)}`;
}
