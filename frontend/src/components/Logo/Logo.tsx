import type { JSX } from 'react/jsx-runtime';
import './Logo.scss';

interface LogoProps {
  headingLevel?: number;
}

export default function Logo({ headingLevel = 1 }: LogoProps) {
  const HeadingTag = `h${headingLevel}` as keyof JSX.IntrinsicElements;

  return (
    <div>
      <HeadingTag className="logo">Hestia</HeadingTag>
    </div>
  );
}
