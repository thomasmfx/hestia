import { useRef } from 'react';
import { Calendar } from 'react-feather';
import './InputDate.scss';

export default function InputDate({ label }: { label: string }) {
  const inputRef = useRef<HTMLInputElement>(null);

  return (
    <div className="input-date">
      <label className="input-date__label">{label}</label>
      <div className="input-date__control">
        <input ref={inputRef} className="input-date__input" type="date" />
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
