import { useRef } from 'react';
import { Calendar } from 'react-feather';
import './InputDate.scss';

interface InputDateProps {
  label: string;
  /** Quando `value`/`onChange` são passados, o campo é controlado. */
  value?: string;
  onChange?: (value: string) => void;
  autoComplete?: string;
}

export default function InputDate({
  label,
  value,
  onChange,
  autoComplete,
}: InputDateProps) {
  const inputRef = useRef<HTMLInputElement>(null);

  return (
    <div className="input-date">
      <label className="input-date__label">{label}</label>
      <div className="input-date__control">
        <input
          ref={inputRef}
          className="input-date__input"
          type="date"
          value={value}
          onChange={onChange ? (e) => onChange(e.target.value) : undefined}
          autoComplete={autoComplete}
        />
        <button
          type="button"
          className="input-date__icon"
          onClick={() => inputRef.current?.showPicker()}
          aria-label="Abrir calendário"
        >
          <Calendar size={16} />
        </button>
      </div>
    </div>
  );
}
