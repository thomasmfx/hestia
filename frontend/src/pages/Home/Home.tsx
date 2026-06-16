import './Home.scss';
import Button from '../../components/Button/Button';
import InputDate from '../../components/InputDate/InputDate';
import InputSelect from '../../components/InputSelect/InputSelect';
import { Search } from 'react-feather';

export default function Home() {
  return (
    <main className="main">
      <div className="home__hero">
        <div className="home__cta">
          <h2 className="home__cta-title">
            Sinta-se em casa, <br></br>a cada estadia.
          </h2>
          <p className="home__cta-description">
            Viva o equilíbrio perfeito entre o luxo boutique e o aconchego de um{' '}
            <br></br> lar. Seu refúgio moderno espera por você.
          </p>
          <div className="home__cta-buttons">
            <InputDate label="Check-in" />
            <InputDate label="Check-out" />
            <InputSelect
              label="Hóspedes"
              options={[
                '1 hóspede',
                '2 hóspedes',
                '3 hóspedes',
                '4 hóspedes',
                '5+ hóspedes',
              ]}
            />
            <Button>
              <Search size={16} />
              Buscar
            </Button>
          </div>
        </div>
      </div>
    </main>
  );
}
