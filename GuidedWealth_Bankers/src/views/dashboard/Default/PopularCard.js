import * as React from 'react';
import PropTypes from 'prop-types';
import { useState } from 'react';

// material-ui
import { styled, useTheme } from '@mui/material/styles';
import {
    FormControlLabel,
    Alert,
    Avatar,
    Box,
    Button,
    CardActions,
    CardContent,
    Divider,
    Grid,
    Menu,
    MenuItem,
    Typography,
    TextField,
    Checkbox
} from '@mui/material';

// project imports
import BajajAreaChartCard from './BajajAreaChartCard';
import MainCard from 'ui-component/cards/MainCard';
import SkeletonPopularCard from 'ui-component/cards/Skeleton/PopularCard';
import { gridSpacing } from 'store/constant';

// assets
import ChevronRightOutlinedIcon from '@mui/icons-material/ChevronRightOutlined';
import MoreHorizOutlinedIcon from '@mui/icons-material/MoreHorizOutlined';
import KeyboardArrowUpOutlinedIcon from '@mui/icons-material/KeyboardArrowUpOutlined';
import KeyboardArrowDownOutlinedIcon from '@mui/icons-material/KeyboardArrowDownOutlined';

// firebase setup
import { initializeApp } from 'firebase/app';
import { firebaseConfig } from 'utils/firebase';
import { getFirestore, onSnapshot, collection, query, getDocs, updateDoc, where, doc } from 'firebase/firestore';
import 'firebase/firestore';
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

// ==============================|| DASHBOARD DEFAULT - POPULAR CARD ||============================== //

const PopularCard = ({ isLoading }) => {
    const theme = useTheme();

    const [anchorEl, setAnchorEl] = useState(null);
    const [showAlert, setAlert] = useState(false);

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const [checked, setChecked] = React.useState([true, true, true, true]);

    const handleChange1 = (e) => {
        setChecked([e.target.checked, e.target.checked, e.target.checked, e.target.checked]);
    };

    const handleChange2 = (e) => {
        setChecked([e.target.checked, checked[1], checked[2], checked[3]]);
    };

    const handleChange3 = (e) => {
        setChecked([checked[0], e.target.checked, checked[2], checked[3]]);
    };

    const handleChange4 = (e) => {
        setChecked([checked[0], checked[1], e.target.checked, checked[3]]);
    };

    const handleChange5 = (e) => {
        setChecked([checked[0], checked[1], checked[2], e.target.checked]);
    };

    const children = (
        <Box sx={{ display: 'flex', flexDirection: 'column', ml: 3 }}>
            <FormControlLabel label="Telegram: @gobigorgohome" control={<Checkbox checked={checked[0]} onChange={handleChange2} />} />
            <FormControlLabel label="Email: jane_flyer@ct.com" control={<Checkbox checked={checked[1]} onChange={handleChange3} />} />
            <FormControlLabel label="Phone: 82738393" control={<Checkbox checked={checked[2]} onChange={handleChange4} />} />
            <FormControlLabel label="Office: 62353535" control={<Checkbox checked={checked[3]} onChange={handleChange5} />} />
        </Box>
    );

    const [summary, setSummary] = useState('');

    const handleSummary = (e) => {
        const { name, value } = e.target;
        setSummary(value);
    };

    const updateUser = async () => {
        const userRef = doc(db, 'client/chad');
        await updateDoc(userRef, {
            lastBankerContact: {
                office: checked[3] ? 62353535 : '',
                phone: checked[2] ? 82738393 : '',
                telegram: checked[0] ? '@gobigorgohome' : '',
                email: checked[1] ? 'jane_flyer@ct.com' : ''
            },
            summary: summary
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log();
        console.log(checked[0]);
        console.log(checked[1]);
        console.log(summary);
        updateUser();
        setAlert(true);
        setTimeout(() => setAlert(false), 3000);
    };

    return (
        <>
            {isLoading ? (
                <SkeletonPopularCard />
            ) : (
                <MainCard content={false}>
                    {showAlert && <Alert severity="success">Successfully shared contact</Alert>}
                    <CardContent>
                        <Grid item>
                            <TextField
                                id="outlined-multiline-static"
                                label="Overall Advice"
                                multiline
                                rows={10}
                                variant="outlined"
                                fullWidth
                                onChange={handleSummary}
                            />
                        </Grid>
                        <p></p>
                        <Grid item alignContent="center">
                            <Typography variant="h2" component="div">
                                Contact Sharing
                            </Typography>
                            <FormControlLabel
                                label="Share All"
                                control={
                                    <Checkbox
                                        checked={checked[0] && checked[1] && checked[2] && checked[3]}
                                        indeterminate={!checked[0] || !checked[1] || !checked[2] || checked[3]}
                                        onChange={handleChange1}
                                    />
                                }
                            />
                            {children}
                        </Grid>
                        <Button onClick={handleSubmit}>Send</Button>
                    </CardContent>
                </MainCard>
            )}
        </>
    );
};

PopularCard.propTypes = {
    isLoading: PropTypes.bool
};

export default PopularCard;
