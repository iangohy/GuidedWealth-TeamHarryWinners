import PropTypes from 'prop-types';
import * as React from 'react';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
// material-ui
import { styled, useTheme } from '@mui/material/styles';
import { Avatar, Box, List, ListItem, ListItemAvatar, ListItemText, Typography, TextField, Button } from '@mui/material';
import { makeStyles } from '@material-ui/core/styles';

// project imports
import MainCard from 'ui-component/cards/MainCard';
import TotalIncomeCard from 'ui-component/cards/Skeleton/TotalIncomeCard';

// assets
import TableChartOutlinedIcon from '@mui/icons-material/TableChartOutlined';

import './StockTable.css';
import Grid from '@material-ui/core/Grid';

// styles
const CardWrapper = styled(MainCard)(({ theme }) => ({
    overflow: 'hidden',
    position: 'relative',
    '&:after': {
        content: '""',
        position: 'absolute',
        width: 210,
        height: 210,
        background: `linear-gradient(210.04deg, ${theme.palette.warning.dark} -50.94%, rgba(144, 202, 249, 0) 83.49%)`,
        borderRadius: '50%',
        top: -30,
        right: -180
    },
    '&:before': {
        content: '""',
        position: 'absolute',
        width: 210,
        height: 210,
        background: `linear-gradient(140.9deg, ${theme.palette.warning.dark} -14.02%, rgba(144, 202, 249, 0) 70.50%)`,
        borderRadius: '50%',
        top: -160,
        right: -130
    }
}));

// ==============================|| DASHBOARD - TOTAL INCOME DARK CARD ||============================== //

const NameCard = ({ isLoading }) => {
    const theme = useTheme();
    const [checked, setChecked] = React.useState([true, false]);

    const handleChange1 = (e) => {
        setChecked([e.target.checked, e.target.checked]);
    };

    const handleChange2 = (e) => {
        setChecked([e.target.checked, checked[1]]);
    };

    const handleChange3 = (e) => {
        setChecked([checked[0], e.target.checked]);
    };

    const children = (
        <Box sx={{ display: 'flex', flexDirection: 'column', ml: 3 }}>
            <FormControlLabel label="Telgram" control={<Checkbox checked={checked[0]} onChange={handleChange2} />} />
            <FormControlLabel label="Email" control={<Checkbox checked={checked[1]} onChange={handleChange3} />} />
        </Box>
    );

    const useStyles = makeStyles((theme) => ({
        root: {
            display: 'flex',
            flexGrow: 1,
            padding: theme.spacing(2)
        }
    }));

    const classes = useStyles();

    return (
        <>
            {isLoading ? (
                <TotalIncomeCard />
            ) : (
                <Box sx={{ p: 2.25 }}>
                    <div class="grid2">
                        <Grid item>
                            <TextField
                                id="outlined-multiline-static"
                                label="Overall Advice"
                                multiline
                                rows={10}
                                variant="outlined"
                                fullWidth
                            />
                        </Grid>
                        <Grid item alignContent="center">
                            <Typography variant="h2" component="div">
                                Contact Sharing
                            </Typography>
                            <FormControlLabel
                                label="Share All"
                                control={
                                    <Checkbox
                                        checked={checked[0] && checked[1]}
                                        indeterminate={checked[0] !== checked[1]}
                                        onChange={handleChange1}
                                    />
                                }
                            />
                            {children}
                            <Button>Submit</Button>
                        </Grid>
                    </div>
                </Box>
            )}
        </>
    );
};

NameCard.propTypes = {
    isLoading: PropTypes.bool
};

export default NameCard;
