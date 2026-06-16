import { ChevronDown } from 'react-feather';
import './InputSelect.scss';

export default function InputSelect({
  label,
  options,
}: {
  label: string;
  options: string[];
}) {
  return (
    <div className="input-select">
      <label className="input-select__label">{label}</label>
      <div className="input-select__control">
        <select className="input-select__input">
          {options.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
        <ChevronDown className="input-select__chevron" size={16} />
      </div>
    </div>
  );
}
