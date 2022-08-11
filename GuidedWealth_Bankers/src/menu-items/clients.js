// assets
import { IconHeartHandshake, IconHelp } from '@tabler/icons';

// constant
const icons = { IconHeartHandshake, IconHelp };

// ==============================|| CLIENT MENU ITEMS ||============================== //

const clients = {
    id: 'clients',
    title: 'Clients',
    type: 'group',
    children: [
        {
            id: 'clients',
            title: 'All Clients',
            type: 'item',
            url: '/clients/all',
            icon: icons.IconHeartHandshake,
            breadcrumbs: true
        },
        {
            id: 'requests',
            title: 'Requests',
            type: 'item',
            url: '/clients/requests',
            icon: icons.IconHelp,
            breadcrumbs: true
        }
    ]
};

export default clients;
