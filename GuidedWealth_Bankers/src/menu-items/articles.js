// assets
import { IconArticle } from '@tabler/icons';

// constant
const icons = { IconArticle };

// ==============================|| ARTICLE MENU ITEMS ||============================== //

const articles = {
    id: 'articles',
    title: 'Articles',
    type: 'group',
    children: [
        {
            id: 'articles',
            title: 'Articles',
            type: 'item',
            url: '/articles/all',
            icon: icons.IconArticle,
            breadcrumbs: false
        }
    ]
};

export default articles;
