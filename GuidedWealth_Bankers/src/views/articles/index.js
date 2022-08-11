// material-ui
import { Avatar, CardContent, Fab, Grid, Skeleton, Stack, Typography } from '@mui/material';
import MainCard from 'ui-component/cards/MainCard';

// project import
import { IconEye, IconPlus, IconShare, IconThumbUp } from '@tabler/icons';
import { useNavigate } from 'react-router';
import { useEffect, useState } from 'react';

// firebase setup
import { initializeApp } from 'firebase/app';
import { firebaseConfig } from 'utils/firebase';
import { getFirestore, onSnapshot, collection } from 'firebase/firestore';
import 'firebase/firestore';
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);
// ===========================|| ARTICLES ||=========================== //

const Articles = () => {
    const navigate = useNavigate();
    const handleNewArticle = () => {
        console.log('navigating');
        navigate('/articles/new');
    };
    const [articles, setArticles] = useState([]);

    // firebase for articles
    useEffect(() => {
        setArticles([]);
        const colRef = collection(db, 'articles');
        const unsubListener = onSnapshot(colRef, {
            next: (snapshot) => {
                snapshot.docs.map((doc) => {
                    setArticles((articles) => [...articles, doc.data()]);
                });
            },
            error: (err) => {
                // TODO: handle error
            }
        });
        return unsubListener; // <- don't forget to return the unsubscribe function!
    }, []);

    console.log(articles);
    return (
        <>
            {articles.map((article, idx) => (
                <div style={{ marginBottom: 10 }}>
                    <MainCard content={false} boxShadow>
                        {/* <Skeleton variant="rectangular" height={200} /> */}
                        <CardContent sx={{ p: 2 }}>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Typography variant="h2">{article.title}</Typography>
                                </Grid>
                                <Grid item xs={12}>
                                    <Stack direction="row" alignItems="center" spacing={1}>
                                        <Avatar alt="John Doe" src={article.author.profilePictureURL} />
                                        <Stack direction="column">
                                            <Typography variant="h4">{article.author.name}</Typography>
                                            <Typography variant="h6">{article.author.rank}</Typography>
                                        </Stack>
                                    </Stack>
                                </Grid>
                                <Grid item xs={12} sx={{ pt: '8px !important' }}>
                                    <Typography variant="body2">{article.content}</Typography>
                                </Grid>
                                <Grid item xs={6}>
                                    <Stack direction="row" alignItems="center" spacing={0.5}>
                                        <IconThumbUp />
                                        <Typography variant="body2">{article.upvotes}</Typography>
                                        <IconShare />
                                        <Typography variant="body2">{article.shares}</Typography>
                                        <IconEye />
                                        <Typography variant="body2">{article.views}</Typography>
                                    </Stack>
                                </Grid>
                            </Grid>
                        </CardContent>
                    </MainCard>
                </div>
            ))}
            <Fab
                style={{ position: 'fixed', bottom: '50px', right: '50px' }}
                color="primary"
                aria-label="add"
                onClick={() => handleNewArticle()}
            >
                <IconPlus />
            </Fab>
        </>
    );
};

export default Articles;
