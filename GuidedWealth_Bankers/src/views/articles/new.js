// material-ui
import { Alert, AlertTitle, Avatar, Button, CardContent, Fab, Grid, Skeleton, Stack, TextField, Typography } from '@mui/material';
import MainCard from 'ui-component/cards/MainCard';

// project import
import User1 from 'assets/images/users/user-round.svg';
import { IconPlus } from '@tabler/icons';
import { useNavigate } from 'react-router';
import { Box } from '@mui/system';
import { useState } from 'react';

// firebase setup
import { initializeApp } from 'firebase/app';
import { firebaseConfig } from 'utils/firebase';
import { getFirestore, onSnapshot, collection, addDoc, serverTimestamp } from 'firebase/firestore';
import 'firebase/firestore';
import { useEffect } from 'react';
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);
// ===========================|| SKELETON TOTAL GROWTH BAR CHART ||=========================== //

const NewArticle = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [articleBody, setArticleBody] = useState('');

    // get users
    const [users, setUsers] = useState([]);
    const [currentUser, setCurrentUser] = useState({});
    useEffect(() => {
        var usersTemp = [];
        const colRef = collection(db, 'banker');
        const unsubListener = onSnapshot(colRef, {
            next: (snapshot) => {
                snapshot.docs.map((doc) => {
                    usersTemp = usersTemp.concat(doc.data());
                    setUsers((users) => [...users, doc.data()]);
                });
                // for demo purposes, set to 1 user first
                setCurrentUser(usersTemp[0]);
                console.log(usersTemp);
            },
            error: (err) => {
                // TODO: handle error
            }
        });
        return unsubListener; // <- don't forget to return the unsubscribe function!
    }, []);

    const handleCancel = () => {
        setTitle('');
        setArticleBody('');
        navigate('/articles/all');
    };
    const handleSubmit = () => {
        console.log('submitting');
        const articlesRef = collection(db, 'articles');
        addDoc(articlesRef, {
            date: serverTimestamp(),
            title: title,
            content: articleBody,
            upvotes: 0,
            shares: 0,
            views: 0,
            author: currentUser
        }).then(() => {
            navigate('/articles/all');
            return (
                <Alert severity="success">
                    <AlertTitle>Success</AlertTitle>
                    Your article has been <strong>successfully</strong> submitted!
                </Alert>
            );
        });
    };
    return (
        <Box
            component="form"
            sx={{
                width: '100%'
            }}
            noValidate
            autoComplete="off"
        >
            <Stack spacing={3} direction="column">
                <TextField fullWidth placeholder="Title of Article" value={title} onChange={(e) => setTitle(e.target.value)} />
                <TextField
                    fullWidth
                    multiline
                    id="standard-textarea"
                    placeholder="What's on your mind?"
                    rows="40"
                    value={articleBody}
                    onChange={(e) => setArticleBody(e.target.value)}
                />
                <Stack spacing={5} direction="row">
                    <Button
                        variant="outlined"
                        color="error"
                        onClick={() => {
                            handleCancel();
                        }}
                    >
                        Cancel
                    </Button>
                    <Button variant="contained" color="primary" onClick={() => handleSubmit()}>
                        Post Article
                    </Button>
                </Stack>
            </Stack>
        </Box>
    );
};

export default NewArticle;
