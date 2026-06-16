import App from './App';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import UserProfile from './pages/UserProfile/UserProfile';

const routes = [
  {
    path: '/',
    element: <App />,
    children: [
      { index: true, element: <Home /> },
      { path: 'login', element: <Login /> },
      { path: 'profile', element: <UserProfile /> },
    ],
  },
];

export default routes;
