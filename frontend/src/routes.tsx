import App from './App';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import Account from './pages/Account/Account';
import RequireAuth from './components/RequireAuth/RequireAuth';

const routes = [
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: 'login', element: <Login /> },
      {
        path: 'account',
        element: (
          <RequireAuth>
            <Account />
          </RequireAuth>
        ),
      },
    ],
  },
];

export default routes;
