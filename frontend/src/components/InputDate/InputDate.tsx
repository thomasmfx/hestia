import './InputDate.scss';

export default function InputDate({ label }: { label: string }) {
  return (
    <div className="input-date">
      <label className="input-date__label">{label}</label>
      <input className="input-date__input" type="date" />
    </div>
  );
}
