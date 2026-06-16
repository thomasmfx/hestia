import { getPasswordStrength } from '../../lib/password';
import './PasswordStrengthMeter.scss';

// Rótulo + cor por nível de força (amber #b7791f → verde #2d6a4f).
const LEVELS = [
  { label: '', color: '' },
  { label: 'Senha fraca', color: '#b7791f' },
  { label: 'Senha razoável', color: '#cc8b3c' },
  { label: 'Senha boa', color: '#5e8348' },
  { label: 'Senha forte', color: '#2d6a4f' },
];

export default function PasswordStrengthMeter({
  password,
}: {
  password: string;
}) {
  const score = getPasswordStrength(password);
  return (
    <>
      <div className="strength-meter" data-strength={password ? score : 0}>
        <span className="strength-meter__bar" />
        <span className="strength-meter__bar" />
        <span className="strength-meter__bar" />
        <span className="strength-meter__bar" />
      </div>
      {password && (
        <p
          className="strength-meter__hint"
          style={{ color: LEVELS[score].color }}
        >
          {LEVELS[score].label} — mínimo 8 caracteres, com número e maiúscula.
        </p>
      )}
    </>
  );
}
