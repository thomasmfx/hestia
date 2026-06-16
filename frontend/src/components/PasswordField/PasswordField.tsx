import { useState } from 'react';
import { Eye, EyeOff } from 'react-feather';
import TextField from '../TextField/TextField';
import PasswordStrengthMeter from '../PasswordStrengthMeter/PasswordStrengthMeter';
import './PasswordField.scss';

interface PasswordFieldProps {
  value: string;
  onChange: (value: string) => void;
  label?: string;
  placeholder?: string;
  autoComplete?: string;
  /** Mostra o medidor de força abaixo do campo (usado no cadastro). */
  showStrength?: boolean;
}

export default function PasswordField({
  value,
  onChange,
  label = 'Senha',
  placeholder,
  autoComplete,
  showStrength = false,
}: PasswordFieldProps) {
  const [visible, setVisible] = useState(false);
  return (
    <TextField
      label={label}
      type={visible ? 'text' : 'password'}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      autoComplete={autoComplete}
      trailing={
        <button
          type="button"
          className="password-field__eye"
          onClick={() => setVisible((v) => !v)}
          aria-label={visible ? 'Ocultar senha' : 'Mostrar senha'}
        >
          {visible ? <EyeOff size={18} /> : <Eye size={18} />}
        </button>
      }
    >
      {showStrength && <PasswordStrengthMeter password={value} />}
    </TextField>
  );
}
