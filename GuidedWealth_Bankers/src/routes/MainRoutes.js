import { lazy } from 'react';

// project imports
import MainLayout from 'layout/MainLayout';
import Loadable from 'ui-component/Loadable';

// routing
const DashboardDefault = Loadable(lazy(() => import('views/dashboard/Default')));
const Clients = Loadable(lazy(() => import('views/clients')));
const Requests = Loadable(lazy(() => import('views/requests')));
const Articles = Loadable(lazy(() => import('views/articles')));
const NewArticle = Loadable(lazy(() => import('views/articles/new')));

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: <MainLayout />,
    children: [
        {
            path: '/',
            element: <Clients />
        },
        {
            path: 'dashboard',
            children: [
                {
                    path: 'default',
                    element: <DashboardDefault />
                }
            ]
        },
        {
            path: 'clients',
            children: [
                { path: 'all', element: <Clients /> },
                { path: 'requests', element: <Requests /> }
            ]
        },
        {
            path: 'articles',
            children: [
                { path: 'all', element: <Articles /> },
                { path: 'new', element: <NewArticle /> }
            ]
        }
    ]
};

export default MainRoutes;
