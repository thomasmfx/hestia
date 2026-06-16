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
      <select className="input-select__input">
        {options.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
    </div>
  );
}
