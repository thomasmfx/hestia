import { useId } from 'react';
import './TextField.scss';

interface TextFieldProps {
  label: string;
  value: string;
  onChange: (value: string) => void;
  type?: string;
  placeholder?: string;
  autoComplete?: string;
  name?: string;
  /** Adorno renderizado dentro do input (ex.: botão de mostrar senha). */
  trailing?: React.ReactNode;
  /** Conteúdo renderizado abaixo do input (ex.: medidor de força). */
  children?: React.ReactNode;
}

export default function TextField({
  label,
  value,
  onChange,
  type = 'text',
  placeholder,
  autoComplete,
  name,
  trailing,
  children,
}: TextFieldProps) {
  const id = useId();
  return (
    <div className="text-field">
      <label className="text-field__label" htmlFor={id}>
        {label}
      </label>
      <div
        className={`text-field__control${trailing ? ' text-field__control--trailing' : ''}`}
      >
        <input
          id={id}
          className="text-field__input"
          type={type}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder={placeholder}
          autoComplete={autoComplete}
          name={name}
        />
        {trailing && <span className="text-field__trailing">{trailing}</span>}
      </div>
      {children}
    </div>
  );
}
